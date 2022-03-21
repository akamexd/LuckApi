package me.akamex.luckapi.provider.economy;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TransactionResponse {

    private final EconomyResponse paidResponse;
    private final EconomyResponse receiverResponse;

    public TransactionResponse(EconomyResponse paidResponse, EconomyResponse receiverResponse) {
        this.paidResponse = paidResponse;
        this.receiverResponse = receiverResponse;
    }

    public double getAmount() {
        return receiverResponse.getAmount();
    }

    public EconomyResponse getPaidResponse() {
        return paidResponse;
    }

    public EconomyResponse getReceiverResponse() {
        return receiverResponse;
    }

    public boolean isSuccess() {
        return paidResponse.isSuccess() && receiverResponse.isSuccess();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return new EqualsBuilder()
                .append(paidResponse, that.paidResponse)
                .append(receiverResponse, that.receiverResponse)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(paidResponse)
                .append(receiverResponse)
                .toHashCode();
    }
}
