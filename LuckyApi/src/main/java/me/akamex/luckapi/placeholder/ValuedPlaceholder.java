package me.akamex.luckapi.placeholder;

import me.akamex.luckapi.message.AdaptiveMessage;
import me.akamex.luckapi.message.Message;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ValuedPlaceholder<T> extends PlaceholderImpl<T> {

    private T value;

    public ValuedPlaceholder(Placeholder<T> placeholder, T value) {
        super(placeholder.getHolder(), placeholder.getApplier());
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String apply(String message) {
        return message.replaceAll(getHolder(), getApplier().apply(getValue()));
    }

    public AdaptiveMessage apply(Message message) {
        return AdaptiveMessage.adapt(message).placeholder(getHolder(), getApplier().apply(getValue()));
    }

    public String undo(String message) {
        return message.replaceAll(getApplier().apply(getValue()), getHolder());
    }

    public AdaptiveMessage undo(AdaptiveMessage message) {
        return message.placeholder(getApplier().apply(getValue()), getHolder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValuedPlaceholder)) return false;
        final ValuedPlaceholder<?> that = (ValuedPlaceholder<?>) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .toHashCode();
    }

}
