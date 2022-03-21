package me.akamex.luckapi.command;

import me.akamex.luckapi.message.Message;

import java.util.function.Consumer;

public class ExecutingActions {

    ExecutingActions() {
    }

    public Consumer<CommandSession> messageSend(String message) {
        return sendMessage(message);
    }

    public Consumer<CommandSession> messageSend(String... message) {
        return sendMessage(message);
    }

    public Consumer<CommandSession> messageSend(Message message) {
        return sendMessage(message);
    }

    public static Consumer<CommandSession> sendMessage(String message) {
        return session -> session.send(message);
    }

    public static Consumer<CommandSession> sendMessage(String... message) {
        return session -> session.send(message);
    }

    public static Consumer<CommandSession> sendMessage(Message message) {
        return session -> session.send(message);
    }

}
