package me.akamex.luckapi.util.ticking;

import me.akamex.luckapi.LuckServiceApi;

import java.util.function.Supplier;

public interface TickingService extends LuckServiceApi {

    Ticking createTicking(Ticking ticking);

    default Ticking createTicking(long period, Supplier<Boolean> doTick) {
        return new Ticking() {
            @Override
            public boolean doTick() {
                return doTick.get();
            }

            @Override
            public long getPeriod() {
                return period;
            }
        };
    }

    default Ticking createAlwaysTicking(Supplier<Boolean> doTick) {
        return createTicking((AlwaysTicking) doTick::get);
    }

    default Ticking createSecondTicking(Supplier<Boolean> doTick) {
        return createTicking((SecondTicking) doTick::get);
    }

    Ticking removeTicking(Ticking ticking);

}
