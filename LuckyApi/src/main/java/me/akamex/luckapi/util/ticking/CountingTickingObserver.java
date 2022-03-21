package me.akamex.luckapi.util.ticking;

class CountingTickingObserver implements TickingObserver {

    private Ticking ticking;
    private long left;

    CountingTickingObserver(Ticking ticking) {
        this.ticking = ticking;
        left = ticking.getPeriod();
    }

    @Override
    public boolean doTicking() {
        if(--left <= 0) {
            left = ticking.getPeriod();
            return ticking.doTick();
        }
        return false;
    }
}
