package ru.progwards.java2.lessons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Person {
    private String name;
    private int age;
    enum CompareResult {LESS, EQUAL, GREATER};

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

    int sumSequence(int n) {
        if (n == 1)
            return n;
        return sumSequence(n-2)+n;
    }

    public <T> ArrayList from(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        if (array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
        }
        return list;
    }

    public <E> void swap(List<E> list, int one, int two) {
        E e = list.get(one);
        list.set(one, list.get(two));
        list.set(two, e);
    }

    public static <T extends Comparable<T>> CompareResult compare(T t1, T t2) {
        int i = t1.compareTo(t2);
        if (i == -1) {
            return CompareResult.LESS;
        } else if (i == 0) {
            return CompareResult.EQUAL;
        } else {
            return CompareResult.GREATER;
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
        System.out.println(p.sumSequence(3));
    }
}