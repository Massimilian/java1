package classes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Consultation {
    private LocalDateTime start;
    private LocalDateTime finish;
    private String student;
    private String professor;
    private String thema;

    public Consultation(LocalDateTime start, String thema, String professor, String student) {
        this.start = start;
        this.finish = start.plusMinutes(15).minusNanos(1);
        this.thema = thema.equals("") ? "Тема не указана" : thema;
        this.professor = professor;
        this.student = student;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public String getStudent() {
        return this.student;
    }

    public String getProfessor() {
        return this.professor;
    }

    public String getThema() {
        return this.thema;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
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

    public static Consultation findByString(ArrayList<Consultation> consults, String dtn) {
        Consultation result = null;
        String professorName = getProfessorFromString(dtn);
        LocalDateTime current = Consultation.prepareDateTimeByString(dtn);
        for (int i = 0; i < consults.size(); i++) {
            if (current.isEqual(consults.get(i).getStart()) && consults.get(i).getProfessor().equals(professorName)) {
                result = consults.get(i);
                break;
            }
        }
        return result;
    }

    public static String getProfessorFromString(String str) {
        StringBuilder sb = new StringBuilder();
        boolean isLetter = true;
        int count = str.length();
        do {
            char ch = str.charAt(--count);
            if (Character.isDigit(ch)) {
                isLetter = false;
            } else {
                sb.append(ch);
            }
        } while (isLetter);
        return sb.reverse().toString();
    }

    private static LocalDateTime prepareDateTimeByString(String datetime) {
        if (!(datetime.charAt(12) == '1' || datetime.charAt(12) == '2')) {
            datetime = String.format("%s%s%s", datetime.substring(0, 12), "0", datetime.substring(12));
        }
        return LocalDateTime.parse(datetime.substring(0, 17).replace(", ", "T"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consultation that = (Consultation) o;
        return Objects.equals(start, that.start) && Objects.equals(finish, that.finish) && Objects.equals(professor, that.professor) && Objects.equals(thema, that.thema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish, professor, thema);
    }
}
