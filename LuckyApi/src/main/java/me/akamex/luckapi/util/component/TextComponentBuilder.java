package me.akamex.luckapi.util.component;

import me.akamex.luckapi.api.Creatable;
import me.akamex.luckapi.chat.click.ChatClickAction;
import me.akamex.luckapi.chat.click.ChatClickActionService;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import static me.akamex.luckapi.util.color.ColorUtils.color;

public class TextComponentBuilder implements Creatable<TextComponent> {

    private ChatClickActionService service;
    private TextComponent component;

    private TextComponentBuilder(ChatClickActionService service, TextComponent component) {
        this.service = service;
        this.component = component;
    }

    public static TextComponentBuilder newBuilder() {
        return new TextComponentBuilder(null, new TextComponent());
    }

    public static TextComponentBuilder from(TextComponent component) {
        return new TextComponentBuilder(null, component);
    }

    public static TextComponentBuilder newBuilder(ChatClickActionService service) {
        return new TextComponentBuilder(service, new TextComponent());
    }

    public static TextComponentBuilder from(ChatClickActionService service, TextComponent component) {
        return new TextComponentBuilder(service, component);
    }

    public TextComponentBuilder setText(String text) {
        component.setText(color(text));
        return this;
    }

    public TextComponentBuilder onClick(Consumer<Player> action, boolean removeOnClick) {
        ChatClickAction.Builder chatClickAction = ChatClickAction.newBuilder()
                .setAction(action)
                .setRemoveOnClick(removeOnClick);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, service.createAction(chatClickAction).getCommand()));
        return this;
    }

    public TextComponentBuilder onClick(Consumer<Player> action) {
        return onClick(action, true);
    }

    public TextComponentBuilder onClick(ClickEvent.Action action, String value) {
        component.setClickEvent(new ClickEvent(action, value));
        return this;
    }

    public TextComponentBuilder onHover(HoverEvent.Action action, String value) {
        component.setHoverEvent(new HoverEvent(action, new Text(color(value))));
        return this;
    }

    public TextComponentBuilder setExtra(Collection<BaseComponent> components) {
        component.setExtra(new ArrayList<>(components));
        return this;
    }

    public TextComponentBuilder appendExtra(TextComponent component) {
        component.addExtra(component);
        return this;
    }

    public TextComponentBuilder appendExtra(TextComponentBuilder builder) {
        return appendExtra(builder.create());
    }

    @Override
    public TextComponent create() {
        return component.duplicate();
    }
}
