package me.akamex.luckapi.config.adapter;

import org.bukkit.configuration.Configuration;

import java.util.function.Consumer;

public class DefaultAdapterVersion extends AbstractAdapterVersion {

    private final Consumer<Configuration> consumer;

    public DefaultAdapterVersion(double version, Consumer<Configuration> consumer) {
        super(version);
        this.consumer = consumer;
    }

    public DefaultAdapterVersion(double version) {
        this(version, configuration -> {});
    }

    @Override
    public void apply(Configuration config) {
        consumer.accept(config);
    }

    @Override
    public String toString() {
        return "DefaultAdapterVersion{" +
                "version=" + version +
                '}';
    }
}
