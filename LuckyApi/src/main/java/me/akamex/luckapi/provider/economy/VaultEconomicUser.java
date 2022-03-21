package me.akamex.luckapi.provider.economy;

public class VaultEconomicUser extends AbstractEconomicUser<VaultEconomyProvider> {

    VaultEconomicUser(String name, VaultEconomyProvider economyProvider) {
        super(name, economyProvider);
    }

    @Override
    public double getBalance() {
        return economyProvider.getEconomy().getBalance(name);
    }

    @Override
    public EconomyResponse changeBalance(double amount) {
        net.milkbowl.vault.economy.EconomyResponse response;
        if(amount == 0) {
            return new EconomyResponse(getBalance(), 0, true);
        }
        if(amount > 0) {
            response = economyProvider.getEconomy().depositPlayer(name, amount);
        } else {
            response = economyProvider.getEconomy().withdrawPlayer(name, -amount);
        }

        return new EconomyResponse(response.balance, response.amount, response.transactionSuccess());
    }

    @Override
    public TransactionResponse transactionOpen(EconomicUser user, double amount) {
        return null;
    }
}
