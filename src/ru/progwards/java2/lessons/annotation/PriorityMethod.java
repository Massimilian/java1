package ru.progwards.java2.lessons.annotation;

import java.lang.reflect.Method;

public class PriorityMethod implements Comparable<PriorityMethod>{
    Method method;
    int priority;

    public PriorityMethod(Method method, int priority) {
        this.method = method;
        this.priority = priority;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public int compareTo(PriorityMethod o) {
        return Integer.compare(this.priority, o.priority);
    }
}
