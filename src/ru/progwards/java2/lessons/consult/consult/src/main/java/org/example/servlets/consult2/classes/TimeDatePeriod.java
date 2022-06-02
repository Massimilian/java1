package org.example.servlets.consult2.classes;

public class TimeDatePeriod {
    private TimePeriod time;
    private String NameOfDay;

    public TimeDatePeriod(String nameOfDay, TimePeriod time) {
        this.time = time;
        NameOfDay = nameOfDay;
    }

    public TimePeriod getTime() {
        return time;
    }

    public String getNameOfDay() {
        return NameOfDay;
    }
}
