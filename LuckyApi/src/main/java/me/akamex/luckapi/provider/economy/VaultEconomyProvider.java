package me.akamex.luckapi.provider.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;

public class VaultEconomyProvider extends PooledEconomyProvider {

    private final Economy economy;

    VaultEconomyProvider() {
        this.economy = Bukkit.getServicesManager().load(Economy.class);
    }

    Economy getEconomy() {
        return economy;
    }

    @Override
    protected Callable<EconomicUser> getEconomicUser(String name) {
        return () -> new VaultEconomicUser(name, this);
    }
}
