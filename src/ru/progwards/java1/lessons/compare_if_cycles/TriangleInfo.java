package ru.progwards.java1.lessons.compare_if_cycles;

import org.junit.Assert;

public class TriangleInfo {
    private static boolean check(int a, int b, int c) {
        return a < b + c;
    }

    private static boolean pifagorus(int a, int b, int c) {
        return a * a == b * b + c * c;
    }

    public static boolean isTriangle(int a, int b, int c) {
        return check(a, b, c) && check(b, c, a) && check(c, a, b) && a > 0 && b > 0 && c > 0;
    }

    public static boolean isRightTriangle(int a, int b, int c) {
        return isTriangle(a, b, c) && (pifagorus(a, b, c) || pifagorus(b, c, a) || pifagorus(c, a, b));
    }

    public static boolean isIsoscelesTriangle(int a, int b, int c) {
        return isTriangle(a, b, c) && (a == b || b == c || a == c);
    }

    public static void main(String[] args) {
        Assert.assertTrue(isTriangle(4, 4, 6));
        Assert.assertTrue(isTriangle(4, 6, 4));
        Assert.assertTrue(isTriangle(6, 4, 4));
        Assert.assertFalse(isTriangle(1, 0, 1));
        Assert.assertTrue(isRightTriangle(3, 4, 5));
        Assert.assertTrue(isRightTriangle(4, 5, 3));
        Assert.assertTrue(isRightTriangle(5, 3, 4));
        Assert.assertFalse(isRightTriangle(3, 4, 6));
        Assert.assertTrue(isIsoscelesTriangle(5, 5, 1));
        Assert.assertTrue(isIsoscelesTriangle(5, 1, 5));
        Assert.assertTrue(isIsoscelesTriangle(1, 5, 5));
        Assert.assertFalse(isIsoscelesTriangle(0, 0, 3));
        Assert.assertFalse(isIsoscelesTriangle(10, 9, 3));
    }
}
