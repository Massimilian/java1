package org.example.servlets.consult2.classes;

import java.time.LocalTime;
import java.util.ArrayList;

public class School {
    private ArrayList<Professor> professors = new ArrayList<>();

    public School() {
        Professor valery = new Professor("Valery");
        valery.setWorkTime("Tuesday", LocalTime.of(18, 0), LocalTime.of(20, 0));
        valery.setWorkTime("Wednesday", LocalTime.of(12, 0), LocalTime.of(14, 0));
        Professor nikita = new Professor("Nikita");
        nikita.setWorkTime("Thursday", LocalTime.of(20, 0), LocalTime.of(21, 0));
        professors.add(valery);
        professors.add(nikita);
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }
}
