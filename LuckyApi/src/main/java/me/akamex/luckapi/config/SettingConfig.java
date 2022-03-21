package me.akamex.luckapi.config;

import me.akamex.luckapi.config.adapter.AdapterVersion;
import me.akamex.luckapi.config.adapter.ConfigAdapter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.Consumer;

public class SettingConfig extends Config {

    protected final ConfigAdapter configAdapter;

    public SettingConfig(Plugin plugin, String name, Consumer<ConfigAdapter> configAdapterConsumer) {
        super(plugin, name);
        reload();

        configAdapter = new ConfigAdapter(file, config);
        configAdapterConsumer.accept(configAdapter);
        configAdapter.applyAll();
    }

    public SettingConfig(Plugin plugin, String name) {
        super(plugin, name);
        reload();

        configAdapter = new ConfigAdapter(file, config);
    }

    public SettingConfig(Plugin plugin) {
        this(plugin, "config");
        reload();
    }

    protected SettingConfig registerAdapterVersion(AdapterVersion adapterVersion) {
        configAdapter.registerAdapter(adapterVersion);
        return this;
    }

    @Override
    protected void load(YamlConfiguration config) {
    }

    @SuppressWarnings("unchecked")
    public <E> E getOption(String path) {
        return (E) config.get(path);
    }

    @SuppressWarnings("unchecked")
    public <E> E getOption(String path, Class<E> clazz) {
        return (E) config.get(path);
    }

    @SuppressWarnings("unchecked")
    public <E> E getOption(String path, E defaultValue) {
        return (E) config.get(path, defaultValue);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public double getDouble(String path, double defaultValue) {
        return config.getDouble(path, defaultValue);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public ConfigurationSection getSection(String path) {
        return config.getConfigurationSection(path);
    }

    public List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    //

    @SuppressWarnings("unchecked")
    public <E> E getOption(Path path) {
        return (E) config.get(path.getPath());
    }

    @SuppressWarnings("unchecked")
    public <E> E getOption(Path path, Class<E> clazz) {
        return (E) config.get(path.getPath());
    }

    @SuppressWarnings("unchecked")
    public <E> E getOption(Path path, E defaultValue) {
        return (E) config.get(path.getPath(), defaultValue);
    }

    public String getString(Path path) {
        return config.getString(path.getPath());
    }

    public String getString(Path path, String defaultValue) {
        return config.getString(path.getPath(), defaultValue);
    }

    public int getInt(Path path) {
        return config.getInt(path.getPath());
    }

    public int getInt(Path path, int defaultValue) {
        return config.getInt(path.getPath(), defaultValue);
    }

    public double getDouble(Path path) {
        return config.getDouble(path.getPath());
    }

    public double getDouble(Path path, double defaultValue) {
        return config.getDouble(path.getPath(), defaultValue);
    }

    public boolean getBoolean(Path path) {
        return config.getBoolean(path.getPath());
    }

    public boolean getBoolean(Path path, boolean defaultValue) {
        return config.getBoolean(path.getPath(), defaultValue);
    }

    public List<?> getList(Path path) {
        return config.getList(path.getPath());
    }

    public List<String> getStringList(Path path) {
        return config.getStringList(path.getPath());
    }

    public ConfigurationSection getSection(Path path) {
        return config.getConfigurationSection(path.getPath());
    }

    public List<Integer> getIntegerList(Path path) {
        return config.getIntegerList(path.getPath());
    }

}
