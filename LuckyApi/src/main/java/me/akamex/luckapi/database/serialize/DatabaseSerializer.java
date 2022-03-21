package me.akamex.luckapi.database.serialize;

import me.akamex.luckapi.database.Database;

public interface DatabaseSerializer<C> {

    <T extends Database> T deserialize(C section, String filePath);

    default <T extends Database> C serialize(T database) {
        throw new UnsupportedOperationException();
    }

}
