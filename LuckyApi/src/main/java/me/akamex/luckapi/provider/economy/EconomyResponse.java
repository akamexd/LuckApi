package me.akamex.luckapi.provider.economy;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EconomyResponse {

    private final double balanceBefore;
    private final double balanceAfter;
    private final double amount;
    private final boolean success;

    public EconomyResponse(double balance, double amount, boolean success) {
        this.balanceBefore = balance - amount;
        this.balanceAfter = balance;
        this.amount = amount;
        this.success = success;
    }

    public double getBeforeBalance() {
        return balanceBefore;
    }

    public double getAfterBalance() {
        return balanceAfter;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EconomyResponse that = (EconomyResponse) o;
        return new EqualsBuilder()
                .append(balanceBefore, that.balanceBefore)
                .append(balanceAfter, that.balanceAfter)
                .append(amount, that.amount)
                .append(success, that.success)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(balanceBefore)
                .append(balanceAfter)
                .append(amount)
                .append(success)
                .toHashCode();
    }
}
