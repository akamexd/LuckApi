package me.akamex.luckapi.util.ticking;

public interface Ticking {

    /**
     * @return result, if true - removes ticking, if false - continue
     */
    boolean doTick();

    long getPeriod();

}
