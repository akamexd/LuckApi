package me.akamex.luckapi.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public final class ConfigurationUtil {

    private ConfigurationUtil() {
        throw new UnsupportedOperationException();
    }

    public static ConfigurationSection getSection(Configuration configuration, String path) {
        ConfigurationSection section = configuration.getConfigurationSection(path);
        return section == null ? configuration.createSection(path) : section;
    }

    public static ConfigurationSection clearSection(Configuration configuration, String path) {
        if(configuration.getConfigurationSection(path) != null) {
            configuration.set(path, "");
        }
        return configuration.createSection(path);
    }

    public static ConfigurationSection checkSection(Configuration configuration, String path) {
        ConfigurationSection section = configuration.getConfigurationSection(path);
        if (section == null) {
            throw new NullPointerException("This path of the configuration section is incorrect!");
        }
        return section;
    }

}
