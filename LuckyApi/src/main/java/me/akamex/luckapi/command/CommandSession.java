package me.akamex.luckapi.command;

import me.akamex.luckapi.message.Message;

public class CommandSession {

    private final Executor executor;
    private final Arguments arguments;

    CommandSession(Executor executor, Arguments arguments) {
        this.executor = executor;
        this.arguments = arguments;
    }

    public Executor getExecutor() {
        return executor;
    }

    public Arguments getArguments() {
        return arguments;
    }

    CommandSession nextToStart() {
        arguments.nextToStart();
        return this;
    }

    public void send(String message) {
        executor.send(message);
    }

    public void send(String... messages) {
        executor.send(messages);
    }

    public void send(Message message) {
        executor.send(message);
    }
}
