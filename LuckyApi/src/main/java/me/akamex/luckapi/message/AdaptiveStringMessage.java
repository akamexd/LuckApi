package me.akamex.luckapi.message;

import me.akamex.luckapi.util.color.ColorUtils;

public class AdaptiveStringMessage extends StringMessage implements AdaptiveMessage {

    AdaptiveStringMessage(StringMessage message) {
        super(message.toRawText());
    }

    @Override
    public AdaptiveMessage placeholder(String holder, Object to) {
        setMessage(toRawText().replaceAll(holder, ColorUtils.color(to.toString())));
        return this;
    }
}
