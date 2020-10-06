package ru.progwards.java1.lessons.arrays;

import org.junit.Assert;
import ru.progwards.java1.lessons.interfaces.CompareWeight;

import java.util.Arrays;

public class ArraySort {
    /**
     * Simple sort method
     * @param a (array for sort)
     */
    public static void sort(int[] a) {
        if (a.length > 1) {
            for (int i = 0; i < a.length - 1; i++) {
                for (int j = i + 1; j < a.length; j++) {
                    if (a[i] > a[j]) {
                        a[i] += a[j];
                        a[j] = a[i] - a[j];
                        a[i] = a[i] - a[j];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] ints = {5, 3, 2, 4, 1};
        int[] unSorted = {5, 4, 3, 2, 1};
        sort(ints);
        Arrays.sort(unSorted);
        Assert.assertArrayEquals(ints, unSorted);
    }
}
