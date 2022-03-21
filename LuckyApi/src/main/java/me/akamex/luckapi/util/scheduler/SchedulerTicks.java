package me.akamex.luckapi.util.scheduler;

import me.akamex.luckapi.util.date.DateUtil;
import me.akamex.luckapi.util.date.DateZone;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public final class SchedulerTicks {

    public static final long SECOND = 20;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;

    private SchedulerTicks() {
        throw new UnsupportedOperationException();
    }

    public static long toTicks(long duration, TimeUnit unit) {
        return unit.toSeconds(duration) * SECOND;
    }

    public static long untilDate(LocalDateTime time) {
        return untilDate(DateZone.MOSCOW, time);
    }

    public static long untilDate(DateZone zone, LocalDateTime time) {
        final LocalDateTime now = DateUtil.getDate(zone);

        long until = now.until(time, ChronoUnit.SECONDS);
        while (until <= 0) {
            time = time.plusDays(1);
            until = now.until(time, ChronoUnit.SECONDS);
        }
        return until * SECOND;
    }

}
