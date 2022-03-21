package me.akamex.luckapi.message;

import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComponentMessage implements Message {

    private TextComponent component;

    public ComponentMessage(TextComponent component) {
        this.component = component;
    }

    TextComponent getComponent() {
        return component;
    }

    @Override
    public String toRawText() {
        return component.toLegacyText();
    }

    @Override
    public void send(CommandSender sender) {
        String text = component.getText();
        if(text.isEmpty()) {
            return;
        }

        if(sender instanceof Player) {
            ((Player) sender).spigot().sendMessage(component);
        } else {
            sender.sendMessage(toRawText());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentMessage that = (ComponentMessage) o;
        return new EqualsBuilder().append(component, that.component).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(component).toHashCode();
    }
}
