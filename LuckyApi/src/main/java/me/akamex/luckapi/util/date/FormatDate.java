package me.akamex.luckapi.util.date;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public enum FormatDate {

    DATE("dd/MM/yyyy"),
    DATE_TIME("HH:mm:ss dd/MM/yyyy"),
    HOURS_MINUTES("hh:mm"),
    DAY_MONTH("dd/MM"),
    HOURS_MINUTES_SECONDS("hh:mm:ss");

    private SimpleDateFormat simpleFormat;
    private DateTimeFormatter timeFormat;

    FormatDate(String pattern) {
        this.simpleFormat = new SimpleDateFormat(pattern);
        this.timeFormat = DateTimeFormatter.ofPattern(pattern);
    }

    public SimpleDateFormat getSimpleFormat() {
        return simpleFormat;
    }

    public DateTimeFormatter getTimeFormat() {
        return timeFormat;
    }

}
