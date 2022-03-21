package me.akamex.luckapi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.function.Consumer;

public abstract class HikariDatabase implements Database {

    protected HikariDataSource dataSource;

    protected HikariDatabase(Consumer<HikariConfig> configConsumer) {
        HikariConfig config = new HikariConfig();
        configConsumer.accept(config);
        dataSource = new HikariDataSource(config);
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public boolean isClosed() {
        return dataSource.isClosed();
    }

    @Override
    public void close() {
        if(isClosed()) {
            return;
        }
        dataSource.close();
    }

}
