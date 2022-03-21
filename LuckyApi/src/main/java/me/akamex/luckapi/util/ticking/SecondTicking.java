package me.akamex.luckapi.util.ticking;

import me.akamex.luckapi.util.scheduler.SchedulerTicks;

public interface SecondTicking extends Ticking {

    @Override
    default long getPeriod() {
        return SchedulerTicks.SECOND;
    }
}
