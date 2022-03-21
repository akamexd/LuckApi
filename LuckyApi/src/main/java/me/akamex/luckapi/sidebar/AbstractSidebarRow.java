package me.akamex.luckapi.sidebar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public abstract class AbstractSidebarRow implements SidebarRow {

    protected String id;
    protected List<BiConsumer<Player, String>> updateCallbacks = new ArrayList<>();

    protected AbstractSidebarRow(String id) {
        this.id = id;
    }

    protected AbstractSidebarRow() {
        this(UUID.randomUUID().toString().substring(32));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addUpdateCallback(BiConsumer<Player, String> updateCallback) {
        updateCallbacks.add(updateCallback);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSidebarRow that = (AbstractSidebarRow) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(updateCallbacks, that.updateCallbacks)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37
        ).append(id)
                .append(updateCallbacks)
                .toHashCode();
    }
}
