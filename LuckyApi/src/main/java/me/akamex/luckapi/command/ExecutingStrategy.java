package me.akamex.luckapi.command;

import me.akamex.luckapi.api.Creatable;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface ExecutingStrategy {

    /**
     * TODO
     * Написать билдер для таб комплитера нормальный (на выходе (create) либо функцию, либо интерфейс)
     */

    void onCommand(CommandSession session);

    default List<String> onTabComplete(CommandSession session) {
        return new ArrayList<>();
    }

    default ExecutingStrategy setOnCommand(Consumer<CommandSession> onCommand) {
        return this;
    }

    default ExecutingStrategy setOnTabComplete(Function<CommandSession, List<String>> onTabComplete) {
        return this;
    }

    static Builder newBuilder() {
        return new Builder();
    }

    class Builder {

        private Builder() {
        }

        public abstract static class ConfirmedBuilder implements Creatable<ExecutingStrategy> {
        }

        public CommandBuilder commandStrategy() {
            return new CommandBuilder();
        }

        public static class CommandBuilder extends ConfirmedBuilder {

            private List<Predicate<CommandSession>> predicates = new ArrayList<>();
            private List<Consumer<CommandSession>> actions = new ArrayList<>();

            private CommandBuilder() {
            }

            public CommandBuilder addCheck(BiPredicate<Executor, Arguments> predicate, BiConsumer<Executor, Arguments> ifFalse) {
                predicates.add(session -> {
                    if(!predicate.test(session.getExecutor(), session.getArguments())) {
                        ifFalse.accept(session.getExecutor(), session.getArguments());
                        return false;
                    }
                    return true;
                });
                return this;
            }

            public CommandBuilder addCheck(BiPredicate<Executor, Arguments> predicate) {
                predicates.add(session -> predicate.test(session.getExecutor(), session.getArguments()));
                return this;
            }

            public CommandBuilder addAction(BiConsumer<Executor, Arguments> action) {
                actions.add(session -> action.accept(session.getExecutor(), session.getArguments()));
                return this;
            }

            public CommandBuilder addCheck(Predicate<CommandSession> predicate, Consumer<CommandSession> ifFalse) {
                predicates.add(session -> {
                    if(!predicate.test(session)) {
                        ifFalse.accept(session);
                        return false;
                    }
                    return true;
                });
                return this;
            }

            public CommandBuilder addCheck(Predicate<CommandSession> predicate) {
                predicates.add(predicate);
                return this;
            }

            public CommandBuilder addAction(Consumer<CommandSession> action) {
                actions.add(action);
                return this;
            }

            @Override
            public ExecutingStrategy create() {
                return new ExecutingStrategyImpl(session -> {
                    for(Predicate<CommandSession> predicate : predicates) {
                        if(!predicate.test(session)) {
                            return;
                        }
                    }

                    for(Consumer<CommandSession> action : actions) {
                        action.accept(session);
                    }
                });
            }

            public ExecutingStrategy create(Function<CommandSession, List<String>> tabCompleter) {
                return new ExecutingStrategyImpl(session -> {
                    for(Predicate<CommandSession> predicate : predicates) {
                        if(!predicate.test(session)) {
                            return;
                        }
                    }

                    for(Consumer<CommandSession> action : actions) {
                        action.accept(session);
                    }
                }, tabCompleter);
            }
        }

        public SubCommandBuilder subCommandStrategy() {
            return new SubCommandBuilder();
        }

        public static class SubCommandBuilder extends ConfirmedBuilder {

            private List<Predicate<CommandSession>> predicates = new ArrayList<>();
            private List<Consumer<CommandSession>> argumentsEmpty = new ArrayList<>();
            private List<Consumer<CommandSession>> subCommandNotFound = new ArrayList<>();
            private Map<List<String>, ExecutingStrategy> subCommandMap = new HashMap<>();

            private SubCommandBuilder() {
            }

            public SubCommandBuilder addCheck(Predicate<CommandSession> predicate, Consumer<CommandSession> ifFalse) {
                predicates.add(session -> {
                    if(!predicate.test(session)) {
                        ifFalse.accept(session);
                        return false;
                    }
                    return true;
                });
                return this;
            }

            public SubCommandBuilder addCheck(Predicate<CommandSession> predicate) {
                predicates.add(predicate);
                return this;
            }

            public SubCommandBuilder whenArgumentAbsent(Consumer<CommandSession> action) {
                argumentsEmpty.add(action);
                return this;
            }

            public SubCommandBuilder whenSubCommandAbsent(Consumer<CommandSession> action) {
                subCommandNotFound.add(action);
                return this;
            }

            public SubCommandBuilder addCheck(BiPredicate<Executor, Arguments> predicate, BiConsumer<Executor, Arguments> ifFalse) {
                predicates.add(session -> {
                    if(!predicate.test(session.getExecutor(), session.getArguments())) {
                        ifFalse.accept(session.getExecutor(), session.getArguments());
                        return false;
                    }
                    return true;
                });
                return this;
            }

            public SubCommandBuilder addCheck(BiPredicate<Executor, Arguments> predicate) {
                predicates.add(session -> predicate.test(session.getExecutor(), session.getArguments()));
                return this;
            }

            public SubCommandBuilder whenArgumentAbsent(BiConsumer<Executor, Arguments> action) {
                argumentsEmpty.add(session -> action.accept(session.getExecutor(), session.getArguments()));
                return this;
            }

            public SubCommandBuilder whenSubCommandAbsent(BiConsumer<Executor, Arguments> action) {
                subCommandNotFound.add(session -> action.accept(session.getExecutor(), session.getArguments()));
                return this;
            }

            public SubCommandBuilder addSubCommand(String label, ExecutingStrategy strategy, String... labels) {
                subCommandMap.put(Stream.concat(Stream.of(label), Arrays.stream(labels)).map(String::toLowerCase).collect(Collectors.toList()), strategy);
                return this;
            }

            public SubCommandBuilder addSubCommand(String label, ExecutingStrategy.Builder.ConfirmedBuilder strategy, String... labels) {
                return addSubCommand(label, strategy.create(), labels);
            }

            @Deprecated
            public SubCommandBuilder addSubCommand(ExecutingStrategy strategy, String... labels) {
                subCommandMap.put(Arrays.stream(labels).map(String::toLowerCase).collect(Collectors.toList()), strategy);
                return this;
            }

            @Deprecated
            public SubCommandBuilder addSubCommand(ExecutingStrategy.Builder.ConfirmedBuilder strategy, String... labels) {
                return addSubCommand(strategy.create(), labels);
            }

            @Override
            public ExecutingStrategy create() {
                return new ExecutingStrategyImpl(session -> {
                    for(Predicate<CommandSession> predicate : predicates) {
                        if(!predicate.test(session.nextToStart())) {
                            return;
                        }
                    }

                    if(session.getArguments().length() == 0) {
                        for(Consumer<CommandSession> action : argumentsEmpty) {
                            action.accept(session.nextToStart());
                        }
                        return;
                    }

                    String argument = session.getArguments().next();
                    for(List<String> labels : subCommandMap.keySet()) {
                        if(labels.contains(argument.toLowerCase())) {
                            ExecutingStrategy strategy = subCommandMap.get(labels);
                            strategy.onCommand(new CommandSession(session.getExecutor(), Arguments.removeFirst(session.getArguments())));
                            return;
                        }
                    }

                    for(Consumer<CommandSession> action : subCommandNotFound) {
                        action.accept(session.nextToStart());
                    }
                });
            }

            public ExecutingStrategy create(Function<CommandSession, List<String>> tabCompleter) {
                return new ExecutingStrategyImpl(session -> {
                    for(Predicate<CommandSession> predicate : predicates) {
                        if(!predicate.test(session.nextToStart())) {
                            return;
                        }
                    }

                    if(session.getArguments().length() == 0) {
                        for(Consumer<CommandSession> action : argumentsEmpty) {
                            action.accept(session.nextToStart());
                        }
                        return;
                    }

                    String argument = session.getArguments().next();
                    for(List<String> labels : subCommandMap.keySet()) {
                        if(labels.contains(argument.toLowerCase())) {
                            ExecutingStrategy strategy = subCommandMap.get(labels);
                            strategy.onCommand(new CommandSession(session.getExecutor(), Arguments.removeFirst(session.getArguments())));
                            return;
                        }
                    }

                    for(Consumer<CommandSession> action : subCommandNotFound) {
                        action.accept(session.nextToStart());
                    }
                }, tabCompleter);
            }
        }

    }

}
