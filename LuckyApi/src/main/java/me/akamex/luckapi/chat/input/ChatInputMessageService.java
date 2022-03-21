package me.akamex.luckapi.chat.input;

import me.akamex.luckapi.util.function.Optionality;
import me.akamex.luckapi.LuckServiceApi;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface ChatInputMessageService extends LuckServiceApi {

    ChatInputMessage createMessage(ChatInputMessage message);

    default ChatInputMessage createMessage(ChatInputMessage.Builder builder) {
        return createMessage(builder.create());
    }

    Optionality<ChatInputMessage> getMessage(UUID uuid);

    default Optionality<ChatInputMessage> getMessage(Player player) {
        return getMessage(player.getUniqueId());
    }

    Optionality<ChatInputMessage> removeMessage(UUID uuid);

    default Optionality<ChatInputMessage> removeMessage(Player player) {
        return getMessage(player.getUniqueId());
    }

    ChatInputMessage removeMessage(ChatInputMessage inputMessage);


}
