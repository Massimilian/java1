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
        if (Files.exists(path)) {
            try {
                String json = Files.readString(path);
                Type type = new TypeToken<School>() {
                }.getType();
                school = new Gson().fromJson(json, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            school.deletePassedConsults();
        } else {
            school = new School();
        }
        return school;
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

    public ArrayList<String> getProfessorsNames() {
        return (ArrayList<String>) professors.stream().map(professor -> professor.getName()).collect(Collectors.toList());
    }

    public String getNames(boolean isProf) {
        StringBuilder sb = new StringBuilder();
        sb.append(isProf? "Преподаватели школы: " : "Студенты школы: ");
        for (int i = 0; i < (isProf? professors : students).size(); i++) {
            sb.append((isProf? professors : students).get(i).getName()).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), ".");
        return sb.toString();
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

    private Person getPersonByName(String name, boolean isProf) {
        Person result = null;
        for (int i = 0; i < (isProf? professors.size() : students.size()); i++) {
            if ((isProf? professors : students).get(i).getName().equals(name)) {
                result = (isProf? professors : students).get(i);
                break;
            }
        }
        return result;
    }

    public boolean addPerson(Person person) {
        boolean result = false;
        System.out.println(person.getClass().getName().contains("Student"));
        if (person.getClass().getName().contains("Student")) {
            if (!students.contains(person)) {
                result = this.students.add((Student) person);
            }
        }
        if (person.getClass().getName().contains("Professor")) {
            if (!professors.contains(person)) {
                result = this.professors.add((Professor) person);
            }
        }
        return result;
    }

    public boolean deleteStudent(String name) {
        return students.remove(getPersonByName(name, false));
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public boolean deleteProfessor(String name) {
        return professors.remove(getPersonByName(name, true));
    }

    public boolean deletePerson(String name, boolean isProf) {
        return isProf? professors.remove(getPersonByName(name, true)) : students.remove(getPersonByName(name, false));
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
                    status = students.get(i).getPassword().equals(password) ? "" : "Неправильный пароль, попробуйте ещё раз или обратитесь за помощью к администратору.";
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
