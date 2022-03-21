package me.akamex.luckapi.database.serialize;

import me.akamex.luckapi.util.Enums;
import me.akamex.luckapi.database.ConnectionType;
import me.akamex.luckapi.database.Database;
import org.bukkit.configuration.ConfigurationSection;

public final class DatabaseSerializers {

    private DatabaseSerializers() {
        throw new UnsupportedOperationException();
    }

    public static YamlDatabaseSerializer yaml() {
        return new YamlDatabaseSerializerImpl();
    }

    private static class YamlDatabaseSerializerImpl implements YamlDatabaseSerializer {

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Database> T deserialize(ConfigurationSection section, String filePath) {
            ConnectionType type = Enums.valueOf(ConnectionType.values(), section.getString("type", "MYSQL"));
            if(type == null) {
                throw new IllegalArgumentException("Connection type not found!");
            }
            if(type.getType() == Database.Builder.BuilderType.HIKARI) {
                return (T) Database.newBuilder()
                        .hikariBuilder()
                        .setType(type)
                        .setHostname(section.getString("hostname", "127.0.0.1"))
                        .setPort(section.getString("port", "3306"))
                        .setDatabase(section.getString("database", ""))
                        .setUsername(section.getString("username", "root"))
                        .setPassword(section.getString("password", "root"))
                        .setUrl(filePath)
                        .createDatabase();
            }
            throw new IllegalArgumentException("Builder type not registered as implementation!");
        }
    }

}
