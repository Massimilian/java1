package lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Insurance {
    /**
     * Enum for constructor and setDuration method.
     */
    public static enum FormatStyle {
        SHORT, LONG, FULL
    }

    private ZonedDateTime start;
    private Duration duration;


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


    public ZonedDateTime getStart() {
        return start;
    }

    public Duration getDuration() {
        return duration;
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
            this.duration = Duration.parse(strDuration);
        }
    }

    /**
     * Special method to parse String to LocalDateTime
     *
     * @param strDuration String for parse
     * @return Duration between parsed LocalDateTime and now
     */
    private Duration parser(String strDuration) {
        ArrayList<Integer> prepared = prepare(strDuration);
        LocalDateTime ceil = LocalDateTime.now().plusYears(prepared.get(0)).plusMonths(prepared.get(1)).
                plusDays(prepared.get(2)).plusHours(prepared.get(3)).
                plusMinutes(prepared.get(4)).plusSeconds(prepared.get(5));
        return Duration.between(LocalDateTime.now(), ceil);
    }

    /**
     * Method to get information from String into Array format
     *
     * @param strDuration for prepare
     * @return prepared ArrayList
     */
    private ArrayList<Integer> prepare(String strDuration) {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(strDuration.split("[:T-]")));
        ArrayList<Integer> result = new ArrayList<>();
        for (String s : arr) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

    /**
     * Method to check the validation of this Insurance
     *
     * @param dateTime date and time for check
     * @return is valid
     */
    public boolean checkValid(ZonedDateTime dateTime) {
        boolean result = true;
        if (duration != null) {
//            Instant insDateTime = dateTime.toInstant();
//            Instant insStart = start.toInstant();
//            Duration forCheck = Duration.between(insStart, insDateTime);
//            Duration dur = duration.minus(forCheck);
//            result = !dur.isNegative();
            result = !this.duration.minus(Duration.between(start.toInstant(), dateTime.toInstant())).isNegative();
        }
        return result;
    }

    /**
     * Prepare information about this Insurance
     *
     * @return String with full information
     */
    public String toString() {
//        String res = "Insurance issued on " + this.start;
//        String val = " is valid";
//        String notVal = " is not valid";
//        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
//        res += checkValid(zdt)? val : notVal;
//        return res;
        return String.format("Insurance issued on %s %s", this.start.toString(),
                checkValid(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())) ? "is valid" : "is not valid");
    }

    public static void main(String[] args) {
        Insurance insShort = new Insurance("2020-12-01", FormatStyle.SHORT);
        Insurance insLong = new Insurance("2020-12-01T12:00:00", FormatStyle.LONG);
        Insurance insFull = new Insurance("2020-12-01T12:00:00+01:00[Europe/Paris]", FormatStyle.FULL);
        Insurance forCheckingSet = new Insurance("2020-12-01", FormatStyle.SHORT);

        insShort.setDuration(Duration.between(Instant.now(), Instant.now().plusSeconds(31626061))); // 1 year, 1 day, 1 hour, 1 minute, 1 second
        insLong.setDuration(ZonedDateTime.of(LocalDate.of(2021, 12, 2),
                LocalTime.of(13, 1, 1), ZoneId.systemDefault()));
        insFull.setDuration(12, 1, 1);
        forCheckingSet.setDuration("0001-00-01T01:01:01", FormatStyle.LONG);

        assert insShort.getDuration().equals(insLong.getDuration());
        assert insLong.getDuration().equals(insFull.getDuration().plusSeconds(1).plusMinutes(1));
        assert insFull.getDuration().plusSeconds(1).plusMinutes(1).equals(forCheckingSet.getDuration());

        insShort.setDuration(ZonedDateTime.now().plusSeconds(1));
        assert insShort.checkValid(ZonedDateTime.now());
        assert insShort.toString().endsWith("is valid");
        insLong.setDuration(ZonedDateTime.now().minusNanos(1));
        assert !insLong.checkValid(ZonedDateTime.now());
        assert insLong.toString().endsWith("is not valid");
    }
}
