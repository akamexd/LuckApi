package me.akamex.luckapi.title;

import me.akamex.luckapi.placeholder.Placeholder;
import me.akamex.luckapi.placeholder.ValuedPlaceholder;
import me.akamex.luckapi.util.color.ColorUtils;

import static me.akamex.luckapi.util.color.ColorUtils.color;

public class AdaptiveTitle extends Title {

    private AdaptiveTitle(Title title) {
        super(title);
    }

    public static AdaptiveTitle adapt(Title title) {
        return new AdaptiveTitle(title);
    }

    public AdaptiveTitle placeholder(String holder, Object to) {
        String value = ColorUtils.color(to.toString());
        title = title.replaceAll(holder, value);
        subtitle = subtitle.replaceAll(holder, value);
        return this;
    }

    public <T> AdaptiveTitle placeholder(ValuedPlaceholder<T> placeholder) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(placeholder.getValue()));
    }

    public <T> AdaptiveTitle placeholder(Placeholder<T> placeholder, T t) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(t));
    }

}
