package ru.progwards.java2.lessons.testArea;

import java.lang.reflect.*;

public class TestArea {
    Object[] objects = new Object[100];
    Character[] chars;
    Integer[] ints;
    char[] charsTwo;
    Object object;
    Character ch;
    char chTwo;

    public void test(String s) throws ClassNotFoundException, NoSuchMethodException {
        Class clazz = Class.forName(s);
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName() + " - " + fields[i].getType().getSimpleName());
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TestArea ta = new TestArea();
        ta.test("ru.progwards.java2.lessons.testArea.TestArea");
    }
}
