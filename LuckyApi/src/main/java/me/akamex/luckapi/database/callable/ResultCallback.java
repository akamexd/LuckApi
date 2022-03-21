package me.akamex.luckapi.database.callable;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultCallback {

    void execute(ResultSet resultSet) throws Exception;

}
