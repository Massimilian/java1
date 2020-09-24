package ru.progwards.java1.lessons.compare_if_cycles;

import org.junit.Assert;

import static org.hamcrest.core.Is.is;

public class TriangleSimpleInfo {
    public static int maxSide(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static int minSide(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static boolean isEquilateralTriangle(int a, int b, int c) {
        return a == b && b == c;
    }

    public static void main(String[] args) {
        Assert.assertThat(maxSide(3, 5, 7), is(7));
        Assert.assertThat(minSide(3, 5, 7), is(3));
        Assert.assertTrue(isEquilateralTriangle(3, 3, 3));
        Assert.assertFalse(isEquilateralTriangle(3, 4, 3));
    }
}
