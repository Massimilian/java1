package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GettersAndSetters {
    String separator = System.lineSeparator();

    public static void check(String clazz) {
        GettersAndSetters gas = new GettersAndSetters();
        gas.checking(clazz);
    }

    public void checking(String clazz) {
        try {
            Class cl = Class.forName(clazz);
            Field[] fields = cl.getDeclaredFields();
            Method[] methods = cl.getMethods();
            ArrayList<Method> getters = getGettersOrSetters(methods, "get");
            ArrayList<Method> setters = getGettersOrSetters(methods, "set");
            StringBuilder sb = new StringBuilder();
            getMissingMethods(sb, fields, getters, setters);
            System.out.println(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Method> getGettersOrSetters(Method[] methods, String type) {
        ArrayList<Method> gettersOrSetters = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            if (isGetterOrSetter(methods[i], type)) {
                gettersOrSetters.add(methods[i]);
            }
        }
        return gettersOrSetters;
    }

    private boolean isGetterOrSetter(Method method, String type) {
        boolean result = false;
        if (method.getName().matches(String.format("%s%s", type, "[A-Z][\\w]*")) && Modifier.isPublic(method.getModifiers())) {
            result = true;
        }
        return result;
    }

    private void getMissingMethods(StringBuilder sb, Field[] fields, ArrayList<Method> getters, ArrayList<Method> setters) {
        for (int i = 0; i < fields.length; i++) {
            String name = nameWithUpper(fields[i].getName());
            if (missingGetters(fields[i], name, getters)) {
                prepareGetter(sb, fields[i], name);
            }
            if (missingSetters(fields[i], name, setters)) {
                prepareSetter(sb, fields[i], name);
            }
        }
    }

    private boolean missingGetters(Field field, String name, ArrayList<Method> getters) {
        boolean result = true;
        for (int i = 0; i < getters.size(); i++) {
            if (getters.get(i).getName().contains(name) && getters.get(i).getReturnType().equals(field.getType())) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean missingSetters(Field field, String name, ArrayList<Method> setters) {
        boolean result = true;
        for (int i = 0; i < setters.size(); i++) {
            if (setters.get(i).getName().contains(name)) {
                Type[] types = setters.get(i).getParameterTypes();
                if (types.length == 1 && types[0].getTypeName().equals(field.getType().getName())) {
                    result = false;
                }
            }
        }
        return result;
    }

    private String nameWithUpper(String name) {
        String s2 = String.valueOf(name.charAt(0)).toUpperCase();
        name = s2 + name.substring(1);
        return name;
    }

    private void prepareGetter(StringBuilder sb, Field field, String name) {
        sb.append(String.format("public %s get%s();%s", field.getType().getSimpleName(), name, separator));
    }

    private void prepareSetter(StringBuilder sb, Field field, String name) {
        sb.append(String.format("public void set%s(%s %s);%s", name, field.getType().getSimpleName(), field.getName(), separator));
    }

}
