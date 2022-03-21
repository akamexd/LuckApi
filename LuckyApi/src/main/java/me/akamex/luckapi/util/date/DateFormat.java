package me.akamex.luckapi.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public final class DateFormat {

    private DateFormat() {
        throw new UnsupportedOperationException();
    }

    public interface Formatter<T extends TemporalAccessor> {

        default String format(T time, FormatDate format) {
            return format(time, format.getTimeFormat());
        }

        default String format(T time, DateTimeFormatter formatter) {
            return formatter.format(time);
        }

        default T parse(String string, FormatDate format) {
            return parse(string, format.getTimeFormat());
        }

        T parse(String string, DateTimeFormatter formatter);

    }

    public static Formatter<LocalDateTime> dateTimeFormatter() {
        return DateTimeFormatterImpl.INST;
    }

    enum DateTimeFormatterImpl implements Formatter<LocalDateTime> {

        INST;

        @Override
        public LocalDateTime parse(String string, DateTimeFormatter formatter) {
            return LocalDateTime.parse(string, formatter);
        }

    }

    public static Formatter<LocalTime> timeFormatter() {
        return TimeFormatterImpl.INST;
    }

    enum TimeFormatterImpl implements Formatter<LocalTime> {

        INST;

        @Override
        public LocalTime parse(String string, DateTimeFormatter formatter) {
            return LocalTime.parse(string, formatter);
        }

    }

    public static Formatter<LocalDate> dateFormatter() {
        return DateFormatterImpl.INST;
    }

    enum DateFormatterImpl implements Formatter<LocalDate> {

        INST;

        @Override
        public LocalDate parse(String string, DateTimeFormatter formatter) {
            return LocalDate.parse(string, formatter);
        }

    }

}
