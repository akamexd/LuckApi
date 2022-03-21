package me.akamex.luckapi.util.date;

import java.time.ZoneId;
import java.util.TimeZone;

public enum DateZone {

    MOSCOW("Europe/Moscow");

    private TimeZone zone;

    DateZone(String id) {
        this.zone = TimeZone.getTimeZone(ZoneId.of(id));
    }

    public ZoneId getIdentifier() {
        return zone.toZoneId();
    }

    public TimeZone getZone() {
        return zone;
    }

}
