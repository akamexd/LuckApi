package me.akamex.luckapi.database;

import me.akamex.luckapi.database.callable.Callback;
import me.akamex.luckapi.database.callable.ResultCallback;
import org.intellij.lang.annotations.Language;

public interface QueryExecutors {

    QueryExecutors reload(Database database);

    QueryExecutor async();

    QueryExecutor sync();

    interface QueryExecutor {

        void update(@Language("SQL") String sql, Callback callback);

        void update(@Language("SQL") String sql, Callback callback, Object... to);

        default void update(@Language("SQL") String sql) {
            update(sql, result -> {});
        }

        default void update(@Language("SQL") String sql, Object... to) {
            update(sql, result -> {}, to);
        }

        void result(@Language("SQL") String sql, ResultCallback callback);

        void result(@Language("SQL") String sql, ResultCallback callback, Object... to);

    }

}
