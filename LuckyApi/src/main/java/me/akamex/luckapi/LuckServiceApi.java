package me.akamex.luckapi;

import me.akamex.luckapi.api.Cancelable;

public interface LuckServiceApi extends Cancelable {

    default void cancel() {
    }

}
