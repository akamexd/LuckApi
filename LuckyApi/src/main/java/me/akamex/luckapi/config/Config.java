package me.akamex.luckapi.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public abstract class Config {

    protected Plugin plugin;
    protected String name;
    protected YamlConfiguration config;
    protected File file;

    protected Config(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name.endsWith(".yml") ? name : name + ".yml";
    }

    protected abstract void load(YamlConfiguration config);

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void reload() {
        file = new File(plugin.getDataFolder(), name.endsWith(".yml") ? name : name + ".yml");

        File parent = file.getParentFile();
        if(!parent.exists()) {
            parent.mkdirs();
        }
        if(!file.exists()) {
            plugin.saveResource(file.getName(), false);
        }

        config = YamlConfiguration.loadConfiguration(file);
        load(config);
    }

}
