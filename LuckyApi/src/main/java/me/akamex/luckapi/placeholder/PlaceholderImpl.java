package me.akamex.luckapi.placeholder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.function.Function;

class PlaceholderImpl<T> implements Placeholder<T> {

    private String holder;
    private Function<T, String> applier;

    PlaceholderImpl(String holder, Function<T, String> applier) {
        this.holder = holder;
        this.applier = applier;
    }

    @Override
    public String getHolder() {
        return holder;
    }

    @Override
    public Function<T, String> getApplier() {
        return applier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceholderImpl<?> that = (PlaceholderImpl<?>) o;
        return new EqualsBuilder().append(holder, that.holder).append(applier, that.applier).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(holder).append(applier).toHashCode();
    }
}
