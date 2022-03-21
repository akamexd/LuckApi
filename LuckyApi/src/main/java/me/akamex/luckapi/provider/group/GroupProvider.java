package me.akamex.luckapi.provider.group;

import me.akamex.luckapi.api.Service;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GroupProvider extends Service {

    static GroupProvider openProvider() {
        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return new VaultGroupProvider();
        }
        return new EmptyGroupProvider();
    }

    String getGroup(String name);

    String getGroup(UUID uuid);

    String getGroup(OfflinePlayer player);

    String getGroup(Player player);

}
