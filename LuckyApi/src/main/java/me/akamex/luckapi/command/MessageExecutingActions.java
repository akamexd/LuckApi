package me.akamex.luckapi.command;

import me.akamex.luckapi.message.Message;
import me.akamex.luckapi.config.MessageConfig;
import me.akamex.luckapi.config.MessagePath;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class MessageExecutingActions<P extends MessagePath> extends ExecutingActions {

    private final MessageConfig<P> messageConfig;

    public MessageExecutingActions(MessageConfig<P> messageConfig) {
        this.messageConfig = messageConfig;
    }

    public Consumer<CommandSession> messageSend(P path) {
        return sendMessage(messageConfig.getMessage(path));
    }

    public Consumer<CommandSession> messageSend(P path, UnaryOperator<Message> consumer) {
        Message message = messageConfig.getMessage(path);
        message = consumer.apply(message);
        return sendMessage(message);
    }

}
