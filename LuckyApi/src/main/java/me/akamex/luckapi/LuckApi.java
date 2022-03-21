package me.akamex.luckapi;

import me.akamex.luckapi.api.Cancelable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

import java.util.function.Function;

public interface LuckApi extends Cancelable {

    static LuckApi bootstrapWith(Plugin plugin) {
        LuckApi luckApi = Bukkit.getServicesManager().load(LuckApi.class);
        if(luckApi != null) {
            return luckApi;
        }
        luckApi = new HashLuckApi(plugin);
        Bukkit.getServicesManager().register(LuckApi.class, luckApi, luckApi.getPlugin(), ServicePriority.Highest);
        return luckApi;
    }

    boolean isCancelled();

    Plugin getPlugin();

    <T extends LuckServiceApi> T registerService(Class<T> serviceClass, Function<Plugin, T> serviceApi);

    <T extends LuckServiceApi> T unregisterService(Class<T> serviceClass);

    <T extends LuckServiceApi> T getService(Class<T> serviceClass);

}
