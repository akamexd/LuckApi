package me.akamex.luckapi.provider.economy;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class AbstractEconomicUser<T extends EconomyProvider> implements EconomicUser {

    protected final String name;
    protected final T economyProvider;

    protected AbstractEconomicUser(String name, T economyProvider) {
        this.name = name;
        this.economyProvider = economyProvider;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public EconomyProvider getManageProvider() {
        return economyProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEconomicUser<?> that = (AbstractEconomicUser<?>) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(economyProvider, that.economyProvider)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(economyProvider)
                .toHashCode();
    }
}
