package me.akamex.luckapi.database;

public enum ConnectionType {

    MYSQL(Database.Builder.BuilderType.HIKARI),
    SQLITE(Database.Builder.BuilderType.HIKARI),
    H2(Database.Builder.BuilderType.HIKARI);

    private final Database.Builder.BuilderType type;

    ConnectionType(Database.Builder.BuilderType type) {
        this.type = type;
    }

    public Database.Builder.BuilderType getType() {
        return type;
    }
}
