package me.akamex.luckapi.sidebar;

import me.akamex.luckapi.util.color.ColorUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;

public class StaticSidebarRow extends AbstractSidebarRow {

    private String text;

    public StaticSidebarRow(String id, String text) {
        this(id);
        this.text = ColorUtils.color(text);
    }

    public StaticSidebarRow(String text) {
        this.text = text;
    }

    @Override
    public String getText(Player player) {
        return text;
    }

    @Override
    public void update(Player player) {
        updateCallbacks.forEach(callback -> callback.accept(player, text));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaticSidebarRow that = (StaticSidebarRow) o;
        return new EqualsBuilder().appendSuper(super.equals(o)).append(text, that.text).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(text).toHashCode();
    }
}
