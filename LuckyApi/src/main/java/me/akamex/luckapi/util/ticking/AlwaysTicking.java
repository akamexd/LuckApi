package me.akamex.luckapi.util.ticking;

public interface AlwaysTicking extends Ticking {

    @Override
    default long getPeriod() {
        return 1;
    }

}
