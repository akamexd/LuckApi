package me.akamex.luckapi.provider.economy;

public class EmptyEconomyProvider implements EconomyProvider {

    EmptyEconomyProvider() {
    }

    @Override
    public EconomicUser getUser(String name) {
        return new EmptyEconomicUser(name, this);
    }
}
