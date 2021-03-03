package ru.progwards.java2.lessons;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name + " " + age;
    }

    void sortAndPrint(List<Person> list) {
        list.sort(Comparator.comparingInt(a -> a.age));
        list.forEach(System.out::println);
    }

    String reverseChars(String str) {
        if (str.length() <= 1) {
            return str;
        } else {
            return str.substring(str.length() - 1) + reverseChars(str.substring(0, str.length() - 1));
        }
    }

    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("One", 1));
        persons.add(new Person("Four", 4));
        persons.add(new Person("Three", 3));
        persons.add(new Person("Two", 2));
        Person p = new Person("Zero", 0);
        p.sortAndPrint(persons);
        String s = p.reverseChars("54321");
    }
}