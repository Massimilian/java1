package classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class School {
    private static final String placeKeeper = "school.txt";
    private ArrayList<Professor> professors = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();

    private School() {
    }

    public static School getSchool() {
        School school = null;
        Path path = Paths.get(placeKeeper);
        if (Files.exists(path))
            try {
                String json = Files.readString(path);
                Type type = new TypeToken<School>() {
                }.getType();
                school = new Gson().fromJson(json, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        school.deletePassedConsults();
        return school;
//        School school = new School();
//        Professor valery = new Professor("Valery", "admin");
//        valery.setWorkTime(2, LocalTime.of(18, 0), LocalTime.of(20, 0));
//        valery.setWorkTime(3, LocalTime.of(12, 0), LocalTime.of(14, 0));
//        Professor nikita = new Professor("Nikita", "admin");
//        nikita.setWorkTime(4, LocalTime.of(20, 0), LocalTime.of(21, 0));
//        school.professors.add(valery);
//        school.professors.add(nikita);
//        school.students.add(new Student("Tester", "test"));
//        return school;
    }

    public static void close(School school) {
        synchronized (school) {
            String json = new Gson().toJson(school);
            Path path = Paths.get(placeKeeper);
            if (!Files.exists(path)) {
                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.writeString(path, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteAll() {
        this.professors.clear();
        this.students.clear();
    }

    private void deletePassedConsults() {
        for (int i = 0; i < professors.size(); i++) {
            professors.get(i).deletePassedConsults();
        }
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }


    public String getStudentsNamesInString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Студенты школы: ");
        for (int i = 0; i < students.size(); i++) {
            sb.append(students.get(i).getName()).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ".");
        return sb.toString();
    }

    public String getProfessorsNamesInString() {
        ArrayList<String> names = this.getProfessorsNames();
        StringBuilder sb = new StringBuilder();
        sb.append("Преподаватели школы: ");
        for (int i = 0; i < names.size(); i++) {
            sb.append(names.get(i)).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ".");
        return sb.toString();
    }

    public ArrayList<String> getProfessorsNames() {
        return (ArrayList<String>) professors.stream().map(professor -> professor.getName()).collect(Collectors.toList());
    }

    public Professor getProfessorByName(String name) {
        Professor result = null;
        for (int i = 0; i < professors.size(); i++) {
            if (professors.get(i).getName().equals(name)) {
                result = professors.get(i);
                break;
            }
        }
        return result;
    }

    private Student getStudentByName(String name) {
        Student result = null;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(name)) {
                result = students.get(i);
                break;
            }
        }
        return result;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(String name) {
        students.remove(getStudentByName(name));
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void deleteProfessor(String name) {
        professors.remove(getProfessorByName(name));
    }

    public void renovateProfessorByName(String name, Professor prof) {
        professors.remove(this.getProfessorByName(name));
        professors.add(prof);
    }

    public String isCorrect(String name, String password, boolean isStudent) {
        String status = "Неизвестная серверная ошибка";
        if (isStudent) {
            status = "Не существует такого студента";
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getName().equals(name)) {
                    status = students.get(i).getPassword().equals(password) ? "" : "Неправильный пароль, попробуйте ещё раз или обратитесь за помощью к преподавателю";
                    break;
                }
            }
        } else {
            for (int i = 0; i < professors.size(); i++) {
                if (professors.get(i).getName().equals(name) && professors.get(i).getPassword().equals(password)) {
                    status = "";
                    break;
                }
            }
            if (status.equals("Неизвестная серверная ошибка")) {
                status = "У нас нет такого преподавателя";
            }
        }
        return status;
    }

    public ArrayList<Consultation> prepareConsultationListByStudentName(String name) {
        ArrayList<Consultation> consultations = new ArrayList<>();
        for (int i = 0; i < professors.size(); i++) {
            consultations.addAll(professors.get(i).getConsultsByStudentName(name));
        }
        return consultations;
    }

    public void renovateProfessorInfo(Professor professor, Consultation current) {
        synchronized (this) {
            professor.getConsults().remove(current);
            close(this);
        }
    }

}
