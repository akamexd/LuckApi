package me.akamex.luckapi.command;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExecutingStrategyImpl implements ExecutingStrategy {

    private Consumer<CommandSession> onCommand = session -> {};
    private Function<CommandSession, List<String>> onTabComplete = session -> null;

    public ExecutingStrategyImpl(Consumer<CommandSession> onCommand, Function<CommandSession, List<String>> onTabComplete) {
        this.onCommand = onCommand;
        this.onTabComplete = onTabComplete;
    }

    public ExecutingStrategyImpl(Consumer<CommandSession> onCommand) {
        this.onCommand = onCommand;
    }

    public ExecutingStrategyImpl(Function<CommandSession, List<String>> onTabComplete) {
        this.onTabComplete = onTabComplete;
    }

    public ExecutingStrategyImpl() {
    }

    @Override
    public void onCommand(CommandSession session) {
        onCommand.accept(session);
    }

    @Override
    public List<String> onTabComplete(CommandSession session) {
        return onTabComplete.apply(session);
    }

    @Override
    public ExecutingStrategy setOnCommand(Consumer<CommandSession> onCommand) {
        this.onCommand = onCommand;
        return this;
    }

    @Override
    public ExecutingStrategy setOnTabComplete(Function<CommandSession, List<String>> onTabComplete) {
        this.onTabComplete = onTabComplete;
        return this;
    }
}
