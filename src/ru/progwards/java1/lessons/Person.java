package ru.progwards.java1.lessons;

import java.util.Date;
import java.util.Locale;

class Person {
    public String name;
    public Date birth;
    public double salary;

    Person(String name, Date birth, double salary) {
        this.name = name;
        this.birth = birth;
        this. salary = salary;
    }

    void printPersons(Person[] persons) {
        for (int i = 0; i < persons.length; i++) {
            System.out.format(Locale.CANADA_FRENCH, "|%-10s|%2$td/%2$tm/%2$tY|%3$,10.2f|\n", persons[i].name, persons[i].birth, persons[i].salary);
        }
    }
}
