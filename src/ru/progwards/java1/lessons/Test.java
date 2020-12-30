package ru.progwards.java1.lessons;


import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.zone.ZoneRules;
import java.util.Date;
import java.util.Locale;

public class Test {
    Date createDate() {
        return new Date(509922000000L);
    }

    Instant createInstant() {
        return Instant.parse("2020-01-01T15:00:00Z").plusNanos(1);
    }

// Напишите метод, с сигнатурой ZonedDateTime parseZDT(String str),
// который возвращает ZonedDateTime, парся строку вида
// "01.01.2020 16:27:14.444 +0300 Moscow Standard Time"
    ZonedDateTime parseZDT(String str) {
        ZoneRules zg;
        str = String.format("%s:%s", str.substring(0, 27), str.substring(27, 29));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:m:s.SSS z");
        LocalDateTime ldt = LocalDateTime.parse(str, dtf);
        ZonedDateTime z = ZonedDateTime.of(ldt, ZoneId.of("Europe/Moscow"));
        return z;
    }


    public static void main(String[] args) {
        Test test = new Test();
        ZonedDateTime zdt = test.parseZDT("01.01.2020 16:27:14.444 +0300 Moscow Standard Time");
    }
}
