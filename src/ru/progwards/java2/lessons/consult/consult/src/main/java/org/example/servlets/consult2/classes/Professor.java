package org.example.servlets.consult2.classes;

import java.time.LocalTime;
import java.util.ArrayList;

public class Professor {
    private String name;
    ArrayList<Consultation> consults = new ArrayList<>();
    ArrayList<TimeDatePeriod> workTimes = new ArrayList<>();

    public Professor(String name) {
        this.name = name;
    }

    public void setWorkTime(String name, LocalTime start, LocalTime finish) {
        workTimes.add(new TimeDatePeriod(name, new TimePeriod(start, finish)));
    }

    public String getName() {
        return name;
    }

    public String getSchedule() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < workTimes.size(); i++) {
            builder.append(this.getDayName(workTimes.get(i).getNameOfDay())).append(": ");
            builder.append(workTimes.get(i).getTime().toString());
            builder.append("; ");
        }
        builder.setCharAt(builder.length() - 2, '.');
        return builder.toString();
    }

    private String getDayName(String name) {
        String result;
        switch (name) {
            case "Sunday":
                result = "воскресение";
                break;
            case "Monday":
                result = "понедельник";
                break;
            case "Tuesday":
                result = "вторник";
                break;
            case "Wednesday":
                result = "среда";
                break;
            case "Thursday":
                result = "четверг";
                break;
            case "Friday":
                result = "пятница";
                break;
            case "Saturday":
                result = "суббота";
                break;
            default:
                result = name;
                break;
        }
        return result;
    }
}
