package me.akamex.luckapi.sidebar;

import me.akamex.luckapi.util.color.ColorUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class DynamicSidebarRow extends AbstractSidebarRow {

    private Function<Player, String> text;

    public DynamicSidebarRow(String id, Function<Player, String> text) {
        super(id);
        this.text = text;
    }

    public DynamicSidebarRow(Function<Player, String> text) {
        this.text = text;
    }

    @Override
    public String getText(Player player) {
        return ColorUtils.color(text.apply(player));
    }

    @Override
    public void update(Player player) {
        String generated = getText(player);
        updateCallbacks.forEach(callback -> callback.accept(player, generated));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicSidebarRow that = (DynamicSidebarRow) o;
        return new EqualsBuilder().appendSuper(super.equals(o)).append(text, that.text).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(text).toHashCode();
    }
}
