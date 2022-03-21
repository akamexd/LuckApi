package me.akamex.luckapi.util.date;

import java.time.LocalDateTime;

public final class DateUtil {

    private DateUtil() {
        throw new UnsupportedOperationException();
    }

    public static LocalDateTime getDate() {
        return getDate(DateZone.MOSCOW);
    }

    public static LocalDateTime getDate(DateZone zone) {
        return LocalDateTime.now(zone.getIdentifier());
    }

}
