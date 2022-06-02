package org.example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;

public class Consult {
    public static ArrayList<Consultation> consultations = new ArrayList<>();

    public static boolean add(Consultation consultation) {
        return consultations.add(consultation);
    }

    public static String show() {
        Iterator<Consultation> iterator = consultations.iterator();
        StringBuilder sb = new StringBuilder();
        int number = 0;
        while(iterator.hasNext()) {
            sb.append(showCons(iterator.next(), ++number));
        }
        return sb.toString();
    }

    private static String showCons(Consultation next, int number) {
        StringBuilder sb = new StringBuilder();
        sb.append("№" + number + ". ").append(System.lineSeparator());
        sb.append("Student's name: " + next.student + ". ").append(System.lineSeparator());
        sb.append("Mentor's name: " + next.mentor + ". ").append(System.lineSeparator());
        sb.append("Start time: " + Instant.ofEpochMilli(next.start).toString() + ". ").append(System.lineSeparator());
        sb.append("Duration: " + next.duration/3600).append(System.lineSeparator() + ". ");// todo
        sb.append("Comment: " + (next.comment.equals("")? "no comment" : next.comment) + ". ").append(System.lineSeparator()).append(System.lineSeparator());
        return sb.toString();
    }

    public static boolean delete(Consultation consultation) {
        return consultations.remove(consultation);
    }

    public static boolean putNew(String username, String mentor, String start, String time, String duration, String comment) {
        long preparedStart = -1;
        boolean result = deleteIfHasNulls(username, mentor, duration) && (preparedStart = prepareStart(start, time)) != -1;
        if (result) {
            add(new Consultation(mentor, preparedStart, prepareDuration(duration), username, comment));
        }
        return result;
    }

    private static boolean deleteIfHasNulls(String username, String mentor, String duration) {
        return !username.equals("") && !mentor.equals("") && checkDuration(duration);
    }

    private static boolean checkDuration(String duration) {
        return !duration.equals("") && Integer.valueOf(duration) > 0 && Integer.valueOf(duration) <= 60;
    }


    public static long prepareStart(String start, String time) {
        String[] date = prepareDateOrTime(start, "-");
        String[] timing = prepareDateOrTime(time, ":");
        long result;
        if (date.length != 3 && timing.length != 2) {
            result = -1;
        } else {
            LocalDateTime ldt = LocalDateTime.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]),
                    Integer.valueOf(date[2]), Integer.valueOf(timing[0]), Integer.valueOf(timing[1]));
            Instant inst = ldt.toInstant(ZoneOffset.UTC);
            LocalDateTime deadline = LocalDateTime.now().plusHours(1);
            result = ldt.isAfter(deadline)? inst.toEpochMilli(): -1;// запись может быть сделана минимум за один час
        }
        return result;
    }

    private static String[] prepareDateOrTime(String start, String regex) {
        return start.split(regex);
    }

    private static long prepareDuration(String dur) {
        return Integer.valueOf(dur) * 60000;
    }
}
