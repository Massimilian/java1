package ru.progwards.java2.lessons.generics;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArraySort {
    public static <T extends Comparable> T[] sort(T... args) {
        for (int i = 0; i < args.length - 1; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i].compareTo(args[j]) == -1) { // перепутал право и лево, простите...
                    T temp = args[j];
                    args[j] = args[i];
                    args[i] = temp;
                }
            }
        }
//        Object[] temp = Arrays.asList(args).stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList()).toArray();
//        for (int i = 0; i < temp.length; i++) {
//            args[i] = (T) temp[i];
//        }
        return args;
    }
}
