package me.akamex.luckapi.config.adapter;

import org.bukkit.configuration.Configuration;

public interface AdapterVersion {

    double getVersion();

    void apply(Configuration config);

}
