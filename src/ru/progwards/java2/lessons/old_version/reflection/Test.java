package ru.progwards.java2.lessons.old_version.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test implements Comparable<Test> {
    public String test1;
    private int test2;

    protected Test() {
    }

    public Test(String test1, int test2) {
        this.test1 = test1;
        this.test2 = test2;
    }

    public int getTest2() {
        return test2;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public void ask() {
        ask2();
    }

    private void ask2() {
        System.out.println("Method ask2.");
    }

    @Override
    public int compareTo(Test o) {
        return test2 - o.getTest2();
    }

    void callSetName(Person person, String name) {
        Class clazz = person.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod("setName", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
        try {
            method.invoke(person, name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    void setName(Person person, String name) {
        Class clazz = person.getClass();
        try {
            Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);
            field.set(person, name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    Person callConstructor(String name){
        Person p = new Person();
        Class clazz = p.getClass();
        Object empl = null;
        try {
            Constructor constr = clazz.getDeclaredConstructor(String.class);
            constr.setAccessible(true);
            empl = constr.newInstance(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (Person) empl;
    }
}
