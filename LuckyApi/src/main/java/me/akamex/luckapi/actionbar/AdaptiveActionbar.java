package me.akamex.luckapi.actionbar;

import me.akamex.luckapi.placeholder.Placeholder;
import me.akamex.luckapi.placeholder.ValuedPlaceholder;
import me.akamex.luckapi.util.color.ColorUtils;

import static me.akamex.luckapi.util.color.ColorUtils.color;

public class AdaptiveActionbar extends Actionbar {

    private AdaptiveActionbar(Actionbar actionbar) {
        super(actionbar);
    }

    public static AdaptiveActionbar adapt(Actionbar actionbar) {
        return new AdaptiveActionbar(actionbar);
    }

    public AdaptiveActionbar placeholder(String holder, Object to) {
        text = text.replaceAll(holder, ColorUtils.color(to.toString()));
        return this;
    }

    public <T> AdaptiveActionbar placeholder(ValuedPlaceholder<T> placeholder) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(placeholder.getValue()));
    }

    public <T> AdaptiveActionbar placeholder(Placeholder<T> placeholder, T t) {
        return placeholder(placeholder.getHolder(), placeholder.getApplier().apply(t));
    }

}
