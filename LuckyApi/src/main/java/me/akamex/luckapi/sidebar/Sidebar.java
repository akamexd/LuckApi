package me.akamex.luckapi.sidebar;

import me.akamex.luckapi.api.Creatable;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;

public interface Sidebar {

    String getName();

    List<SidebarRow> getRows();

    default SidebarRow getRow(String id) {
        for(SidebarRow row : getRows()) {
            if(row.getId().equals(id)) {
                return row;
            }
        }
        return null;
    }

    default void update(Player player) {
        getRows().forEach(row -> row.update(player));
    }

    void show(Player player);

    void hide(Player player);

    static Builder newBuilder() {
        return new Builder();
    }

    class Builder implements Creatable<Sidebar> {

        protected String name = UUID.randomUUID().toString().substring(32);
        protected Function<Player, String> titleGenerator = pl -> "Scoreboard";
        protected List<SidebarRow> rows = new ArrayList<>();

        protected Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTitleGenerator(Function<Player, String> titleGenerator) {
            this.titleGenerator = titleGenerator;
            return this;
        }

        public Builder addRow(SidebarRow row) {
            rows.add(row);
            return this;
        }

        @Override
        public Sidebar create() {
            return new SidebarImpl(name, titleGenerator, rows);
        }
    }

}
