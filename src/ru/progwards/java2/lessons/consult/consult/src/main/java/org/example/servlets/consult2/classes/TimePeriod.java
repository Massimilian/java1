package org.example.servlets.consult2.classes;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimePeriod {
    LocalTime start;
    LocalTime finish;

    public TimePeriod(LocalTime start, LocalTime finish) {
        this.start = start;
        this.finish = finish;
    }

    @Override
    public String toString() {
        return start.toString() + " - " + finish.toString();
    }
}
