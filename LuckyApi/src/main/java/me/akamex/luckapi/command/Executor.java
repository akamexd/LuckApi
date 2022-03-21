package me.akamex.luckapi.command;

import me.akamex.luckapi.api.Handle;
import me.akamex.luckapi.message.Message;
import me.akamex.luckapi.util.color.ColorUtils;
import me.akamex.luckapi.util.permission.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public interface Executor extends Handle<CommandSender> {

    default boolean hasPermission(String permission) {
        return hasPermission(PermissionNode.node(permission));
    }

    default boolean hasPermission(PermissionNode node) {
        return node.checkPermission(getHandle());
    }

    default boolean isPlayer() {
        return getHandle() instanceof Player;
    }

    default Player getPlayer() throws ClassCastException {
        return (Player) getHandle();
    }

    default void send(String message) {
        getHandle().sendMessage(ColorUtils.color(message));
    }

    default void send(String... messages) {
        Arrays.asList(messages).forEach(this::send);
    }

    default void send(Message message) {
        message.send(getHandle());
    }

}
