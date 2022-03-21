package me.akamex.luckapi.provider.economy;

public class EmptyEconomicUser extends AbstractEconomicUser<EconomyProvider> {

    EmptyEconomicUser(String name, EconomyProvider economyProvider) {
        super(name, economyProvider);
    }

    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public EconomyResponse changeBalance(double amount) {
        return new EconomyResponse(0, 0, false);
    }

    @Override
    public TransactionResponse transactionOpen(EconomicUser user, double amount) {
        return new TransactionResponse(user.changeBalance(-amount), changeBalance(amount));
    }
}
