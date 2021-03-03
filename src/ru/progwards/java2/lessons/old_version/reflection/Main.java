package ru.progwards.java2.lessons.old_version.reflection;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassInspector.inspect("ru.progwards.java2.lessons.old_version.reflection.Test");
        GettersAndSetters.check("ru.progwards.java2.lessons.old_version.reflection.Test");
    }
}
