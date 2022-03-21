package me.akamex.luckapi.database;

import me.akamex.luckapi.database.callable.Callback;
import me.akamex.luckapi.database.callable.ResultCallback;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.intellij.lang.annotations.Language;

public class AsyncQueryExecutor extends SyncQueryExecutor {

    private Plugin plugin;

    public AsyncQueryExecutor(Plugin plugin, HikariDatabase database) {
        super(database);
        this.plugin = plugin;
    }

    @Override
    public void update(@Language("SQL") String sql, Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> super.update(sql, callback));
    }

    @Override
    public void update(@Language("SQL") String sql, Callback callback, Object... to) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> super.update(sql, callback, to));
    }

    @Override
    public void result(@Language("SQL") String sql, ResultCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> super.result(sql, callback));
    }

    @Override
    public void result(@Language("SQL") String sql, ResultCallback callback, Object... to) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> super.result(sql, callback, to));
    }
}
