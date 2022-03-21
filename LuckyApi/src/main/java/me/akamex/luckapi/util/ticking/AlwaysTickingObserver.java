package me.akamex.luckapi.util.ticking;

class AlwaysTickingObserver implements TickingObserver {

    private Ticking ticking;

    AlwaysTickingObserver(Ticking ticking) {
        this.ticking = ticking;
    }

    @Override
    public boolean doTicking() {
        return ticking.doTick();
    }
}
