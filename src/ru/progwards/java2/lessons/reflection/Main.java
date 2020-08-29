package ru.progwards.java2.lessons.reflection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassInspector.inspect("ru.progwards.java2.lessons.reflection.Test");
        GettersAndSetters.check("ru.progwards.java2.lessons.reflection.Test");
    }
}
