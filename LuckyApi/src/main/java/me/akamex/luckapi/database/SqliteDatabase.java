package me.akamex.luckapi.database;

public class SqliteDatabase extends HikariDatabase {

    public SqliteDatabase(String url, String username, String password) {
        super(config -> {
            config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
            config.setJdbcUrl("jdbc:sqlite:" + url);
            config.setUsername(username);
            config.setPassword(password);
        });
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.SQLITE;
    }
}
