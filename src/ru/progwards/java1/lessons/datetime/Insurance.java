package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Insurance {
    public static enum FormatStyle {
        SHORT, LONG, FULL
    }

    private ZonedDateTime start;
    private Duration duration;

    public ZonedDateTime getStart() {
        return start;
    }

    public Duration getDuration() {
        return duration;
    }

    public Insurance(ZonedDateTime start) {
        this.start = start;
    }

    public Insurance(String strStart, FormatStyle style) {
        try {
            if (style.equals(FormatStyle.SHORT)) {
//                LocalDate ld = LocalDate.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE);
//                LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.of(0, 0, 0));
//                start = ZonedDateTime.of(ldt, ZoneId.of("Europe/Moscow"));
                start = ZonedDateTime.of(LocalDateTime.of(LocalDate.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE),
                            LocalTime.of(0, 0, 0)),
                                ZoneId.of("Europe/Moscow"));
            } else if (style.equals(FormatStyle.LONG)) {
//                LocalDateTime ldt = LocalDateTime.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                start = ZonedDateTime.of(ldt, ZoneId.of("Europe/Moscow"));
                start = ZonedDateTime.of(LocalDateTime.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        ZoneId.of("Europe/Moscow"));
            } else if (style.equals(FormatStyle.FULL)) {
                start = ZonedDateTime.from(DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(strStart));
            }
        } catch (DateTimeParseException dtpe) {
            System.out.println(dtpe.getMessage());
        }
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setDuration(ZonedDateTime expiration) {
//        Instant begin = start.toInstant();
//        Instant end = expiration.toInstant();
//        this.duration = Duration.between(begin, end);
        this.duration = Duration.between(start.toInstant(), expiration.toInstant());
    }

    public void setDuration(int months, int days, int hours) {
//        LocalDateTime ceil = LocalDateTime.now();
//        ceil = ceil.plusMonths(months).minusDays(days).minusHours(hours);
//        this.duration = Duration.between(LocalDateTime.now(), ceil);
        this.duration = Duration.between(LocalDateTime.now(),
                LocalDateTime.now().plusMonths(months).plusDays(days).plusHours(hours));
    }

    public void setDuration(String strDuration, FormatStyle style) {
        if (style.equals(FormatStyle.SHORT)) {
//         long l = Long.parseLong(strDuration);
//         this.duration = Duration.ofMillis(l);
         this.duration = Duration.ofMillis(Long.parseLong(strDuration));
        } else if (style.equals(FormatStyle.LONG)) {
            this.duration = parser(strDuration);
        } else if (style.equals(FormatStyle.FULL)) {
//            String prep = strDuration.substring(0, 25);
//            Instant ceil = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(prep));
//            this.duration = Duration.between(Instant.now(), ceil);
            this.duration = Duration.between(Instant.now(), Instant.from(DateTimeFormatter.ISO_INSTANT.parse(strDuration.substring(0, 25))));
        }
    }

    private Duration parser(String strDuration) {
        ArrayList<Integer> prepared = prepare(strDuration);
        LocalDateTime ceil = LocalDateTime.now().plusYears(prepared.get(0)).plusMonths(prepared.get(1)).
                plusDays(prepared.get(2)).plusHours(prepared.get(3)).
                    plusMinutes(prepared.get(4)).plusSeconds(prepared.get(5));
        return Duration.between(LocalDateTime.now(), ceil);
    }

    private ArrayList<Integer> prepare(String strDuration) {
        ArrayList <String> arr = new ArrayList<>(Arrays.asList(strDuration.split("[:T-]")));
        ArrayList <Integer> result = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            result.add(Integer.parseInt(arr.get(i)));
        }
        return result;
    }


    public static void main(String[] args) {
//        Insurance insShort = new Insurance("2011-12-03", FormatStyle.SHORT);
//        Insurance insLong = new Insurance("2011-12-03T10:15:30", FormatStyle.LONG);
//        Insurance insFull = new Insurance("2011-12-03T10:15:30+01:00[Europe/Paris]", FormatStyle.FULL);

        Insurance test = new Insurance("2020-12-18T00:00:00+03:00[Europe/Moscow]", FormatStyle.FULL);
//        test.setDuration(ZonedDateTime.now());
//        System.out.println(test.getDuration().getSeconds());
        test.setDuration("2020-12-18T12:00:00+03:00[Europe/Moscow]", FormatStyle.FULL);
        System.out.println(test.getDuration().getSeconds());

    }
}
