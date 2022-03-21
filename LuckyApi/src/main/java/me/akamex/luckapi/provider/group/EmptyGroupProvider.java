package me.akamex.luckapi.provider.group;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EmptyGroupProvider implements GroupProvider {

    EmptyGroupProvider() {
    }

    @Override
    public String getGroup(String name) {
        return "";
    }

    @Override
    public String getGroup(UUID uuid) {
        return "";
    }

    @Override
    public String getGroup(OfflinePlayer player) {
        return "";
    }

    @Override
    public String getGroup(Player player) {
        return "";
    }
}
