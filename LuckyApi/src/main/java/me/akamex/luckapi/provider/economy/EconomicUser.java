package me.akamex.luckapi.provider.economy;

import me.akamex.luckapi.util.function.Optionality;
import me.akamex.luckapi.util.player.PlayerFilters;
import org.bukkit.entity.Player;

public interface EconomicUser {

    String getName();

    default Optionality<Player> getPlayerByName() {
        return PlayerFilters.byName(getName());
    }

    EconomyProvider getManageProvider();

    double getBalance();

    default boolean hasBalance(double amount) {
        return getBalance() >= amount;
    }

    EconomyResponse changeBalance(double amount);

    TransactionResponse transactionOpen(EconomicUser user, double amount);

    default TransactionResponse transactionOpen(Player player, double amount) {
        return transactionOpen(getManageProvider().getUser(player), amount);
    }

    default TransactionResponse transactionOpen(String name, double amount) {
        return transactionOpen(getManageProvider().getUser(name), amount);
    }

}
