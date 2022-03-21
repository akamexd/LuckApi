package me.akamex.luckapi.config.adapter;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ConfigAdapter {

    public static final String PATH_TO_VERSION = "configVersion";

    private final File file;
    private final FileConfiguration configuration;

    private final Map<Double, AdapterVersion> adapterVersionMap;

    public ConfigAdapter(File file, FileConfiguration configuration, Map<Double, AdapterVersion> adapterVersionMap) {
        this.file = file;
        this.configuration = configuration;
        this.adapterVersionMap = adapterVersionMap;
    }

    public ConfigAdapter(File file, FileConfiguration configuration) {
        this(file, configuration, new HashMap<>());
    }

    private void saveFile() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeVersion(double version) {
        configuration.set(PATH_TO_VERSION, version);
        saveFile();
    }

    public ConfigAdapter registerAdapter(AdapterVersion adapterVersion) {
        double version = adapterVersion.getVersion();
        if(!adapterVersionMap.containsKey(version)) {
            adapterVersionMap.put(version, adapterVersion);
        }
        return this;
    }

    public double getVersion() {
        return configuration.getDouble(PATH_TO_VERSION, 1.0);
    }

    public AdapterVersion getLastVersionAdapter() {
        return adapterVersionMap.values().stream()
                .sorted(Comparator.comparingDouble(AdapterVersion::getVersion).reversed())
                .limit(1)
                .findFirst().orElse(new DefaultAdapterVersion(1.0));
    }

    public double getLastVersion() {
        return getLastVersionAdapter().getVersion();
    }

    public void applyAll() {
        double versionCurrent = getVersion();
        double lastVersion = getLastVersion();

        adapterVersionMap.values().stream()
                .filter(adapterVersion -> adapterVersion.getVersion() > versionCurrent)
                .sorted(Comparator.comparingDouble(AdapterVersion::getVersion))
                .forEach(adapterVersion -> adapterVersion.apply(configuration));

        writeVersion(lastVersion);
    }

}
