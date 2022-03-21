package me.akamex.luckapi.database.callable;

@FunctionalInterface
public interface Callback {

    void execute(boolean result) throws Exception;

}
