package me.akamex.luckapi.message;

import me.akamex.luckapi.util.color.ColorUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;
import java.util.stream.Collectors;

public class AdaptiveComponentMessage extends ComponentMessage implements AdaptiveMessage {

    AdaptiveComponentMessage(ComponentMessage message) {
        super(message.getComponent().duplicate());
    }

    private TextComponent replaceComponent(TextComponent component, String holder, String to) {
        String text = component.getText();
        if (text != null) {
            component.setText(text.replaceAll(holder, to));
        }
        ClickEvent click = component.getClickEvent();
        if (click != null) {
            String newText = click.getValue().replaceAll(holder, to);
            component.setClickEvent(new ClickEvent(click.getAction(), newText));
        }
        HoverEvent hover = component.getHoverEvent();
        if (hover != null) {
            String newText = TextComponent.toLegacyText(hover.getValue()).replaceAll(holder, to);
            component.setHoverEvent(new HoverEvent(hover.getAction(), TextComponent.fromLegacyText(newText)));
        }
        List<BaseComponent> components = component.getExtra();
        if (components != null && !components.isEmpty()) {
            component.setExtra(components.stream().map(extra -> {
                if (!(extra instanceof TextComponent)) {
                    throw new IllegalArgumentException("The component type does not available!");
                }
                return replaceComponent((TextComponent) extra, holder, to);
            }).collect(Collectors.toList()));
        }
        return component;
    }

    @Override
    public AdaptiveMessage placeholder(String holder, Object to) {
        final String placeholder = ColorUtils.color(to.toString());
        replaceComponent(getComponent(), holder, placeholder);
        return this;
    }

}
