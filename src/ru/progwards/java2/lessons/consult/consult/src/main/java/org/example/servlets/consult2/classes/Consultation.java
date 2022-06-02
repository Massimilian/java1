package org.example.servlets.consult2.classes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Consultation {
    public LocalDateTime start;
    public LocalDateTime finish;

    public Consultation(LocalDateTime start) {
        this.start = start;
        this.finish = start.plusMinutes(15).minusNanos(1);
    }

    public static boolean checkDate(String forCheck, HttpSession session) {
        boolean result = true;
        if (forCheck.length() != 10) {
            session.setAttribute("false", "Неизвестная серверная ошибка");
            result = false;
        } else if (LocalDate.now().isBefore(dateFromString(forCheck)) || LocalDate.now().equals(dateFromString(forCheck))) {
            session.setAttribute("false", "");
        } else {
            session.setAttribute("false", "Некорректная дата");
            result = false;
        }
        return result;
    }

    public static boolean checkTime(String forCheck, HttpSession session) {
        boolean result = true;
        if (forCheck.length() != 5) {
            session.setAttribute("false", "Неизвестная серверная ошибка");
            result = false;
        }
        if (result) {
            LocalDateTime temp = LocalDateTime.of(dateFromString(String.valueOf(session.getAttribute("consultDate"))), timeFromString(forCheck));
            if (LocalDateTime.now().plusHours(2).equals(temp) || LocalDateTime.now().plusHours(2).isAfter(temp)) {
                session.setAttribute("false", "Некорректное время: консультация сегодня в это время уже невозможна.");
                result = false;
            } else {
                session.setAttribute("false", "");
            }
        }
        return result;
    }

    public static LocalDate dateFromString(String forCheck) {
        String[] dates = forCheck.split("-");
        return LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
    }

    public static LocalTime timeFromString(String forCheck) {
        String[] times = forCheck.split(":");
        return LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
    }
}
