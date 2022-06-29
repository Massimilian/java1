package org.example.servlets.consult2.classes;

import java.time.LocalTime;

public class TimePeriod {
    private LocalTime start;
    private LocalTime finish;

    public TimePeriod(LocalTime start, LocalTime finish) {
        this.start = start;
        this.finish = finish;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getFinish() {
        return finish;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setFinish(LocalTime finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return start.toString() + " - " + finish.toString();
    }
}
