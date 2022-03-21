package me.akamex.luckapi.provider.group;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VaultGroupProvider implements GroupProvider {

    private final Permission permission;

    VaultGroupProvider() {
        this.permission = Bukkit.getServicesManager().load(Permission.class);
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
        return permission.getPrimaryGroup(player);
    }
}
