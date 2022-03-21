package me.akamex.luckapi.provider.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface EconomyProvider {

    static EconomyProvider openProvider() {
        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return new VaultEconomyProvider();
        }
        return new EmptyEconomyProvider();
    }

    EconomicUser getUser(String name);

    default EconomicUser getUser(Player player) {
        return getUser(player.getName());
    }

}
