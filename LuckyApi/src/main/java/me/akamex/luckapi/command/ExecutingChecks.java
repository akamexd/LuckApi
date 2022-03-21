package me.akamex.luckapi.command;

import me.akamex.luckapi.util.permission.PermissionNode;

import java.util.function.Predicate;

public final class ExecutingChecks {

    private ExecutingChecks() {
        throw new UnsupportedOperationException();
    }

    public static Predicate<CommandSession> permission(PermissionNode node) {
        return session -> session.getExecutor().hasPermission(node);
    }

    public static Predicate<CommandSession> permission(String node) {
        return session -> session.getExecutor().hasPermission(node);
    }

    public static Predicate<CommandSession> player() {
        return session -> session.getExecutor().isPlayer();
    }

    public static Predicate<CommandSession> server() {
        return session -> !session.getExecutor().isPlayer();
    }

    public static Predicate<CommandSession> argumentsLength(int min, int max) {
        return session -> session.getArguments().length() >= min && session.getArguments().length() <= max;
    }

    public static Predicate<CommandSession> argumentsMin(int min) {
        return argumentsLength(min, Integer.MAX_VALUE);
    }

    public static Predicate<CommandSession> argumentsLength(int value) {
        return session -> session.getArguments().length() == value;
    }

    public static Predicate<CommandSession> argumentFormat(int index, Predicate<String> formatCheck) {
        return session -> formatCheck.test(session.getArguments().get(index));
    }

}
