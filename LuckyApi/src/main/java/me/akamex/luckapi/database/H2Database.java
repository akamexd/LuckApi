package me.akamex.luckapi.database;

public class H2Database extends HikariDatabase {

    public H2Database(String url, String username, String password) {
        super(config -> {
            try {
                Class.forName("org.h2.jdbcx.JdbcDataSource");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            config.setDriverClassName("org.h2.Driver");
            //config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
            config.setJdbcUrl("jdbc:h2://" + url + ";mode=MySQL");
            config.setUsername(username);
            config.setPassword(password);
        });
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.H2;
    }
}
