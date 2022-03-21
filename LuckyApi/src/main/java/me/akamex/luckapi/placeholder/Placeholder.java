package me.akamex.luckapi.placeholder;

import me.akamex.luckapi.message.AdaptiveMessage;
import me.akamex.luckapi.message.Message;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Placeholder<T> extends BiFunction<Message, T, AdaptiveMessage> {

    static <T> Placeholder<T> placeholder(String holder, Function<T, String> function) {
        return new PlaceholderImpl<>(holder, function);
    }

    String getHolder();

    Function<T, String> getApplier();

    default boolean isAvailable(String message) {
        return message.contains(getHolder());
    }

    default boolean isAvailable(Message message) {
        return message.toRawText().contains(getHolder());
    }

    default String apply(String message, T t) {
        return message.replaceAll(getHolder(), getApplier().apply(t));
    }

    @Override
    default AdaptiveMessage apply(Message message, T t) {
        return AdaptiveMessage.adapt(message).placeholder(getHolder(), getApplier().apply(t));
    }

    default String undo(String message, T t) {
        return message.replaceAll(getApplier().apply(t), getHolder());
    }

    default AdaptiveMessage undo(AdaptiveMessage message, T t) {
        return message.placeholder(getApplier().apply(t), getHolder());
    }

    default ValuedPlaceholder<T> toValued(T t) {
        return new ValuedPlaceholder<>(this, t);
    }

}
