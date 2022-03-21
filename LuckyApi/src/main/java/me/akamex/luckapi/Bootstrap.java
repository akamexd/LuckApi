package me.akamex.luckapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "LuckApi", version = "1.0.0-SNAPSHOT")
@Author("luckkyyz")
@Description("Simple library for developing plugins")
public class Bootstrap extends JavaPlugin {

    private LuckApi luckApi;

    @Override
    public void onEnable() {
        luckApi = LuckApi.bootstrapWith(this);
    }

    @Override
    public void onDisable() {
        if(luckApi != null && !luckApi.isCancelled()) {
            luckApi.cancel();
        }
    }
}
