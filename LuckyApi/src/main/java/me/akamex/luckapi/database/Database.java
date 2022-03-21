package me.akamex.luckapi.database;

import me.akamex.luckapi.api.Typable;

import java.io.File;
import java.io.IOException;

public interface Database extends AutoCloseable, Typable<ConnectionType> {

    boolean isClosed();

    static Builder newBuilder() {
        return new Builder();
    }

    class Builder {

        private Builder() {
        }

        public enum BuilderType {

            HIKARI

        }

        public HikariBuilder hikariBuilder() {
            return new HikariBuilder();
        }

        public static class HikariBuilder {

            private ConnectionType type = ConnectionType.MYSQL;
            private String hostname = "localhost", port = "3306", database = "", username = "", password = "", url = "";

            private HikariBuilder() {
            }

            public HikariBuilder setType(ConnectionType type) {
                this.type = type;
                return this;
            }

            public HikariBuilder setHostname(String hostname) {
                this.hostname = hostname;
                return this;
            }

            public HikariBuilder setPort(String port) {
                this.port = port;
                return this;
            }

            public HikariBuilder setPort(int port) {
                return setPort(String.valueOf(port));
            }

            public HikariBuilder setDatabase(String database) {
                this.database = database;
                return this;
            }

            public HikariBuilder setUsername(String username) {
                this.username = username;
                return this;
            }

            public HikariBuilder setPassword(String password) {
                this.password = password;
                return this;
            }

            public HikariBuilder setUrl(String url) {
                return setFile(new File(url));
            }

            @SuppressWarnings("ResultOfMethodCallIgnored")
            public HikariBuilder setFile(File file) {
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }

                url = file.getAbsolutePath();
                return this;
            }

            @SuppressWarnings("unchecked")
            public <T extends HikariDatabase> T createDatabase() {
                if(type == ConnectionType.MYSQL) {
                    return (T) new MysqlDatabase(hostname, port, database, username, password);
                }
                if(type == ConnectionType.SQLITE) {
                    return (T) new SqliteDatabase(url, username, password);
                }
                if(type == ConnectionType.H2) {
                    return (T) new H2Database(url, username, password);
                }
                throw new IllegalArgumentException("Connection type implementation not registered!");
            }
        }

    }

}
