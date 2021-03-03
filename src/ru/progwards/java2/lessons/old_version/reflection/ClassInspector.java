package ru.progwards.java2.lessons.old_version.reflection;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassInspector {
    String separator = System.lineSeparator();
    String step = "    ";

    public static void inspect(String clazz) {
        ClassInspector ci = new ClassInspector();
        ci.insp(clazz);
    }

    public void insp(String clazz) {
        StringBuilder sb = new StringBuilder();
        try {
            Class cl = Class.forName(clazz);
            prepareClass(cl, sb);
            Field[] fields = cl.getDeclaredFields();
            writeFields(fields, sb);
            Constructor[] constructors = cl.getDeclaredConstructors();
            writeConstrs(constructors, sb, cl.getSimpleName());
            Method[] methods = cl.getDeclaredMethods();
            writeMethods(methods, sb);
            sb.append("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        writeFile(sb);
    }

    private void prepareClass(Class cl, StringBuilder sb) {
        sb.append(String.format("%s %s", Modifier.toString(cl.getModifiers()), cl.getSimpleName()));
        if (!cl.getSuperclass().getName().equals("java.lang.Object")) {
            sb.append(String.format(" %s %s", "extends", cl.getSuperclass().getName()));
        }
        Class[] interfaces = cl.getInterfaces();
        if (interfaces.length != 0) {
            sb.append(" implements ");
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < interfaces.length; i++) {
                temp.append(String.format(("%s%s"), interfaces[i].getSimpleName(), ", "));
            }
            sb.append(temp.toString().substring(0, temp.length() - 2));
        }
        sb.append(String.format("%s%s", " {", separator));
    }

    private void writeConstrs(Constructor[] constructors, StringBuilder sb, String className) {
        for (int i = 0; i < constructors.length; i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(String.format("%s%s %s%s", step, Modifier.toString(constructors[i].getModifiers()), className, "("));
            Class[] classes = constructors[i].getParameterTypes();
            for (int j = 0; j < classes.length; j++) {
                temp.append(String.format("%s, ", classes[j].getSimpleName()));
            }
            if (classes.length != 0) {
                sb.append(temp.toString(), 0, temp.length() - 2);
            } else {
                sb.append(temp.toString());
            }
            sb.append(String.format("%s%s", ");", separator));
        }
    }

    private void writeFields(Field[] fields, StringBuilder sb) {
        for (int i = 0; i < fields.length; i++) {
            sb.append(String.format("%s%s %s %s%s%s", step, Modifier.toString(fields[i].getModifiers()), prepareType(fields[i].getType().toString()), fields[i].getName(), ";", separator));
        }
    }

    private String prepareType(String type) {
        return type.substring(type.lastIndexOf('.') + 1);
    }

    private void writeMethods(Method[] methods, StringBuilder sb) {
        for (int i = 0; i < methods.length; i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(String.format("%s%s %s %s%s", step, Modifier.toString(methods[i].getModifiers()), prepareType(methods[i].getReturnType().toString()), methods[i].getName(), "("));
            Type[] types = methods[i].getParameterTypes();
            if (types.length != 0) {
                for (int j = 0; j < types.length; j++) {
                    temp.append(String.format(prepareType(types[j].getTypeName()), ", "));
                }
            }
            sb.append(String.format("%s%s%s", temp.toString().substring(0, temp.length()), ");", separator));
        }
    }

    private void writeFile(StringBuilder sb) {
        Path path = Paths.get("src/ru/progwards/java2/lessons/reflection/file.txt");
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
