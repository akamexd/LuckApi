package me.akamex.luckapi.database;

import org.bukkit.plugin.Plugin;

public class HikariQueryExecutors implements QueryExecutors {

    private final HikariDatabase database;
    private final Plugin plugin;
    private QueryExecutor sync, async;

    @Deprecated
    public HikariQueryExecutors(HikariDatabase database, Plugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }

    public HikariQueryExecutors(Plugin plugin, HikariDatabase database) {
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    public QueryExecutors reload(Database database) {
        if(!(database instanceof HikariDatabase)) {
            return this;
        }

        sync = null;
        async = null;

        return new HikariQueryExecutors(plugin, (HikariDatabase) database);
    }

    @Override
    public QueryExecutor async() {
        if(async == null) {
            async = new AsyncQueryExecutor(plugin, database);
        }
        return async;
    }

    @Override
    public QueryExecutor sync() {
        if(sync == null) {
            sync = new SyncQueryExecutor(database);
        }
        return sync;
    }

}
