package me.akamex.luckapi.placeholder;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public final class Placeholders {

    private Placeholders() {
        throw new UnsupportedOperationException();
    }

    public static Placeholder<Player> name() {
        return Placeholder.placeholder("%name%", HumanEntity::getName);
    }

}
