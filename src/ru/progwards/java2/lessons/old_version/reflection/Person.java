package ru.progwards.java2.lessons.old_version.reflection;

public class Person {
    private String name;

    public Person() {
        name = "no name";
    }

    private Person(String name) {
        this.name = name;
    }

    private void setName(String name) {
        this.name = name;
    }

}
