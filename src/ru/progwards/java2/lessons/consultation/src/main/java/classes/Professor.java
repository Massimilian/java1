package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Professor {
    private String name;
    private String password;
    ArrayList<Consultation> consults = new ArrayList<>();
    ArrayList<TimeDatePeriod> workTimes = new ArrayList<>();

    public Professor(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorkTime(int name, LocalTime start, LocalTime finish) {
        TimeDatePeriod tdp = new TimeDatePeriod(name, new TimePeriod(start, finish));
        int place = findPlaceForWorkTime(tdp);
        workTimes.add(place, tdp);
    }

    private int findPlaceForWorkTime(TimeDatePeriod tdp) {
        int result = this.workTimes.size() == 0 ? 0 : -1;
        for (int i = 0; i < workTimes.size(); i++) {
            if (workTimes.get(i).getNumOfDay() == tdp.getNumOfDay()) {
                if (workTimes.get(i).getTime().getStart().isBefore(tdp.getTime().getStart())) {
                    result = i;
                } else if (workTimes.get(i).getTime().getStart().isAfter(tdp.getTime().getStart())) {
                    result = i + 1;
                }
                if (this.tryMerge(workTimes.get(i), tdp, i)) {
                    result = i;
                }
                break;
            }
            if (workTimes.get(i).getNumOfDay() > tdp.getNumOfDay()) {
                result = i;
                break;
            }
        }
        return result == -1 ? workTimes.size() : result;
    }

    private boolean tryMerge(TimeDatePeriod inWork, TimeDatePeriod tdp, int position) {
        boolean crossed = false;
        if (tdp.getTime().getStart().isBefore(inWork.getTime().getStart()) && tdp.getTime().getFinish().isAfter(inWork.getTime().getFinish())) {
            crossed = true;
        } else if (tdp.getTime().getStart().isAfter(inWork.getTime().getStart()) && tdp.getTime().getFinish().isBefore(inWork.getTime().getFinish())) {
            tdp.getTime().setStart(inWork.getTime().getStart());
            tdp.getTime().setFinish(inWork.getTime().getFinish());
            crossed = true;
        } else if (inWork.getTime().getStart().isAfter(tdp.getTime().getStart()) && inWork.getTime().getStart().isBefore(tdp.getTime().getFinish())) {
            tdp.getTime().setFinish(inWork.getTime().getFinish());
            crossed = true;
        } else if (tdp.getTime().getStart().isAfter(inWork.getTime().getStart()) && tdp.getTime().getStart().isBefore(inWork.getTime().getFinish())) {
            tdp.getTime().setStart(inWork.getTime().getStart());
            crossed = true;
        } else if (tdp.getTime().getStart().equals(inWork.getTime().getFinish())) {
            tdp.getTime().setStart(inWork.getTime().getStart());
            crossed = true;
        } else if (tdp.getTime().getFinish().equals(inWork.getTime().getStart())) {
            tdp.getTime().setFinish(inWork.getTime().getFinish());
            crossed = true;
        }
        if (crossed) {
            workTimes.remove(position);
        }
        return crossed;
    }

    public void setWorkTimes(ArrayList<TimeDatePeriod> workTimes) {
        this.workTimes = workTimes;
    }

    public ArrayList<TimeDatePeriod> getWorkTimes() {
        return this.workTimes;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Consultation> getConsults() {
        return consults;
    }

    public String getSchedule() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < workTimes.size(); i++) {
            builder.append(workTimes.get(i).getNameOfDay()).append(": ");
            builder.append(workTimes.get(i).getTime().toString());
            builder.append("; ");
        }
        if (builder.length() >= 2) {
            builder.setCharAt(builder.length() - 2, '.');
        }
        return builder.toString();
    }

    public String addConsultation(Consultation consult) {
        String result = "";
        int dayName = consult.getStart().getDayOfWeek().getValue();
        if (this.currentDayName(dayName)) {
            if (this.currentTime(consult.getStart().toLocalTime(), consult.getFinish().toLocalTime())) {
                result = this.addIntoArray(consult);
            } else {
                result = "Невозможное время для консультации. Проверьте расписание преподавателя и попробуйте снова.";
            }
        } else {
            result = "Невозможный день недели для консультации. Проверьте расписание преподавателя и попробуйте снова.";
        }
        return result;
    }

    private String addIntoArray(Consultation consult) {
        String result = "";
        if (consults.isEmpty()) {
            consults.add(consult);
        } else if (consults.size() == 1) {
            if (consult.getFinish().isBefore(consults.get(0).getStart())) {
                consults.add(0, consult);
            } else if (consult.getStart().isAfter(consults.get(0).getFinish())) {
                consults.add(consult);
            } else {
                result = "Время консультации уже занято.";
            }
        } else {
            boolean added = false;
            if (consult.getFinish().isBefore(consults.get(0).getStart())) {
                consults.add(0, consult);
                added = true;
            }
            if (!added) {
                for (int i = 0; i < consults.size() - 1; i++) {
                    if (consult.getStart().isAfter(consults.get(i).getFinish()) &&
                            consult.getFinish().isBefore(consults.get(i + 1).getStart())) {
                        consults.add(i + 1, consult);
                        added = true;
                        break;
                    }
                }
            }
            if (!added) {
                if (consult.getStart().isAfter(consults.get(0).getFinish())) {
                    consults.add(consult);
                    added = true;
                }
            }
            result = added ? "" : "Добавление в этот период времени невозможен. Выберите другое время для консультации.";
        }
        return result;
    }

    private boolean currentTime(LocalTime start, LocalTime finish) {
        boolean result = false;
        for (int i = 0; i < workTimes.size(); i++) {
            if ((workTimes.get(i).getTime().getStart().equals(start) || workTimes.get(i).getTime().getStart().isBefore(start)) &&
                    workTimes.get(i).getTime().getFinish().isAfter(finish)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean currentDayName(int dayName) {
        boolean result = false;
        for (int i = 0; i < workTimes.size(); i++) {
            if (workTimes.get(i).getNumOfDay() == dayName) {
                result = true;
                break;
            }
        }
        return result;
    }

    public String getConsultationsScheduleByDay(String date) {
        LocalDate ld = LocalDate.parse(date);
        ArrayList<Consultation> consults = findByDate(ld);
        String result = prepareString(consults);
        if (result.trim().isEmpty()) {
            result = "в этот день консультаций не найдено.";
        }
        return result;
    }

    private String prepareString(ArrayList<Consultation> consults) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < consults.size(); i++) {
            LocalTime temp = consults.get(i).getStart().toLocalTime();
            sb.append(temp.toString()).append(" - ").append(temp.plusMinutes(15).toString()).append(";");
        }
        if (!consults.isEmpty()) {
            sb.replace(sb.length() - 1, sb.length(), ".");
        }
        return sb.toString();
    }

    private ArrayList<Consultation> findByDate(LocalDate ld) {
        return (ArrayList<Consultation>) consults.stream().filter(c -> c.getStart().toLocalDate().equals(ld)).collect(Collectors.toList());
    }

    public void deletePassedConsults() {
        LocalDateTime ldt = LocalDateTime.now();
        consults = (ArrayList<Consultation>) consults.stream().filter(c -> c.getStart().isAfter(ldt)).collect(Collectors.toList());
    }

    public ArrayList<Consultation> getConsultsByStudentName(String name) {
        return (ArrayList<Consultation>) consults.stream().filter(c -> c.getStudent().equals(name)).collect(Collectors.toList());
    }

    public boolean deleteConsult(Consultation consultation) {
        return this.consults.remove(consultation);
    }

    public void renovateConsultsProfessorName() {
        for (int i = 0; i < consults.size(); i++) {
            consults.stream().forEach(c -> c.setProfessor(this.name));
        }
    }
}