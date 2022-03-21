package me.akamex.luckapi.util.math.progress;

import org.bukkit.ChatColor;

public final class ProgressFormats {

    private ProgressFormats() {
        throw new UnsupportedOperationException();
    }

    public static final ProgressFormat DEFAULT = ProgressFormat.builder()
            .setSize(10)
            .setYesSymbol(ChatColor.GREEN + "✔")
            .setNoSymbol(ChatColor.RED + "✗")
            .create();

}
