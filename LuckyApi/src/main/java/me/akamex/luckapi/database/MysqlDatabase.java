package me.akamex.luckapi.database;

public class MysqlDatabase extends HikariDatabase {

    public MysqlDatabase(String hostname, String port, String database, String username, String password) {
        super(config -> {
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database +
                    "?useUnicode=true&allowPublicKeyRetrieval=true&characterEncoding=utf8&useSSL=false");
            config.setUsername(username);
            config.setPassword(password);
        });
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.MYSQL;
    }
}
