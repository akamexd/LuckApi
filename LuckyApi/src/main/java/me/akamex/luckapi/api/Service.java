package me.akamex.luckapi.api;

public interface Service extends Cancelable {

    default void reload() {
    }

}
