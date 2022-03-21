package me.akamex.luckapi.sidebar;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public interface SidebarRow {

    String getId();

    void update(Player player);

    void addUpdateCallback(BiConsumer<Player, String> updateCallback);

    String getText(Player player);

}
