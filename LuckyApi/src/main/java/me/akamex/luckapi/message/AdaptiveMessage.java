package me.akamex.luckapi.message;

import me.akamex.luckapi.placeholder.Placeholder;
import me.akamex.luckapi.placeholder.ValuedPlaceholder;

public interface AdaptiveMessage extends Message {

    static AdaptiveMessage adapt(Message message) {
        if(message instanceof StringMessage) {
            return new AdaptiveStringMessage((StringMessage) message);
        }
        if(message instanceof ComponentMessage) {
            return new AdaptiveComponentMessage((ComponentMessage) message);
        }
        return null;
    }

    default <T> AdaptiveMessage placeholder(ValuedPlaceholder<T> placeholder) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(placeholder.getValue()));
    }

    default <T> AdaptiveMessage placeholder(Placeholder<T> placeholder, T t) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(t));
    }

    AdaptiveMessage placeholder(String holder, Object to);

}
