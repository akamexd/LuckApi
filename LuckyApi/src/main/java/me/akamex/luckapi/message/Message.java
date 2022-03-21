package me.akamex.luckapi.message;

import me.akamex.luckapi.api.Handle;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface Message {

    String toRawText();

    default void send(Handle<? extends CommandSender> handle) {
        send(handle.getHandle());
    }

    void send(CommandSender sender);

    default void send(Collection<CommandSender> senders) {
        senders.forEach(this::send);
    }

    default void broadcast() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    default AdaptiveMessage toAdaptive() {
        return AdaptiveMessage.adapt(this);
    }

    default void sendActionbar(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(toRawText()));
    }

    default void sendActionbar(Collection<Player> players) {
        players.forEach(this::sendActionbar);
    }

    default void broadcastActionbar() {
        Bukkit.getOnlinePlayers().forEach(this::sendActionbar);
    }

    default void sendTitle(Player player, int fadeIn, int stay, int fadeOut) {
        String message = toRawText();
        if(message.isEmpty()) {
            return;
        }
        String[] split = message.split("\n");
        String title = split.length >= 1 ? split[0] : "";
        String subtitle = split.length >= 2 ? split[1] : "";
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    default void sendTitle(Collection<Player> players, int fadeIn, int stay, int fadeOut) {
        players.forEach(player -> sendTitle(player, fadeIn, stay, fadeOut));
    }

    default void broadcastTitle(int fadeIn, int stay, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, fadeIn, stay, fadeOut));
    }

    default void sendTitle(Player player) {
        sendTitle(player, 20, 10, 20);
    }

    default void sendTitle(Collection<Player> players) {
        players.forEach(this::sendTitle);
    }

    default void broadcastTitle() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

}
