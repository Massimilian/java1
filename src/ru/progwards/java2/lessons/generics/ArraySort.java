package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

/**
 * Class to sort different types in array
 */
public class ArraySort {
    /**
     * Main method for sort
     * @param vars elements for sort
     * @param <T> type of elements
     * @return sorted array
     */
    public static <T extends Comparable> T[] sort(T[] vars) {
        for (int i = 0; i < vars.length - 1; i++) {
            for (int j = i + 1; j < vars.length; j++) {
                if (vars[i].compareTo(vars[j]) > 0) {
                    T temp = vars[i];
                    vars[i] = vars[j];
                    vars[j] = temp;
                }
            }
        }
        return vars;
    }

    public static void main(String[] args) {
        Arrays.asList(sort(new Integer[]{3, 2, 1})).equals(new Integer[]{1, 2, 3});
        Arrays.asList(sort(new String[]{"a", "c", "b"})).equals(new String[]{"a", "b", "c"});
    }
}
