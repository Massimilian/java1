package ru.progwards.java1.lessons.interfaces;

public class ArraySort {
    public static void sort(CompareWeight[] a) {
        if (a.length > 1) {
            for (int i = 0; i < a.length - 1; i++) {
                for (int j = i + 1; j < a.length; j++) {
                    if (a[i].compareWeight(a[j]) == CompareWeight.CompareResult.GREATER) {
                        CompareWeight icw = a[i];
                        a[i] = a[j];
                        a[j] = icw;
                    }
                }
            }
        }
    }
}
