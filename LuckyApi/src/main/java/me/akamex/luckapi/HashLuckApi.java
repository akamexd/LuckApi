package me.akamex.luckapi;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * requests, and confirms
 * Queries to database builder
 * item action handler (smart)
 *
 */

@SuppressWarnings("unchecked")
public class HashLuckApi implements LuckApi {

    private Plugin plugin;
    private boolean cancelled;

    private Map<Class<? extends LuckServiceApi>, LuckServiceApi> serviceApiMap = new HashMap<>();

    public HashLuckApi(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public <T extends LuckServiceApi> T registerService(Class<T> serviceClass, Function<Plugin, T> serviceApi) {
        T service = serviceApi.apply(plugin);
        serviceApiMap.put(serviceClass, service);
        return service;
    }

    @Override
    public <T extends LuckServiceApi> T unregisterService(Class<T> serviceClass) {
        return (T) serviceApiMap.remove(serviceClass);
    }

    @Override
    public <T extends LuckServiceApi> T getService(Class<T> serviceClass) {
        if(!serviceApiMap.containsKey(serviceClass)) {
            throw new NullPointerException("Not used key!");
        }
        return (T) serviceApiMap.get(serviceClass);
    }

    @Override
    public void cancel() {
        serviceApiMap.forEach((key, api) -> api.cancel());
        serviceApiMap = null;
        plugin = null;

        cancelled = true;
    }
}
