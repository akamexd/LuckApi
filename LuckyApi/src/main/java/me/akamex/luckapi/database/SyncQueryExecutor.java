package me.akamex.luckapi.database;

import me.akamex.luckapi.database.callable.Callback;
import me.akamex.luckapi.database.callable.ResultCallback;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SyncQueryExecutor implements QueryExecutors.QueryExecutor {

    private HikariDatabase database;

    public SyncQueryExecutor(HikariDatabase database) {
        this.database = database;
    }

    private @Language("SQL") String prepareSql(@Language("SQL") String sql) {
        return sql.endsWith(";") ? sql : sql + ";";
    }

    @Override
    public synchronized void update(@Language("SQL") String sql, Callback callback) {
        try (Connection connection = database.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                callback.execute(statement.execute(prepareSql(sql)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public synchronized void update(@Language("SQL") String sql, Callback callback, Object... to) {
        try (Connection connection = database.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(prepareSql(sql))) {
                for (int x = 0; x < to.length; x++) {
                    statement.setObject(x + 1, to[x]);
                }
                callback.execute(statement.execute());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public synchronized void result(@Language("SQL") String sql, ResultCallback callback) {
        try (Connection connection = database.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try(ResultSet resultSet = statement.executeQuery(prepareSql(sql))) {
                    callback.execute(resultSet);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public synchronized void result(@Language("SQL") String sql, ResultCallback callback, Object... to) {
        try (Connection connection = database.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(prepareSql(sql))) {
                for (int x = 0; x < to.length; x++) {
                    statement.setObject(x + 1, to[x]);
                }
                try(ResultSet resultSet = statement.executeQuery()) {
                    callback.execute(resultSet);
                }
            }
        } catch ( Exception exception) {
            exception.printStackTrace();
        }
    }
}
