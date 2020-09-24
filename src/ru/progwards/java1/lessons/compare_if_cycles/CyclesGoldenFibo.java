package ru.progwards.java1.lessons.compare_if_cycles;

import org.junit.Assert;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CyclesGoldenFibo {
    private static ArrayList<Integer> numbers = (ArrayList<Integer>) Stream.of(1, 1).collect(Collectors.toList());
    private static final double max = 1.61903;
    private static final double min = 1.61703;

    private static boolean checkSide(int a, int b, int c) {
        double checker = a > b ? (double) a / (double) b : (double) b / (double) a;
        return checker <= max && checker >= min && a == c;
    }

    private static boolean buildTriangolo(int i) {
        boolean found = false;
        for (int j = 0; j < 100; j++) {
            if (j > i && checkSide(j, i, j)) {
                found = true;
                System.out.println(String.format("Найден подходящий треугольник с длиной ребра %d и основанием %d.", j, i));
            }
        }
        return found;
    }

    public static boolean containsDigit(int number, int digit) {
        return Integer.toString(number).contains(Integer.toString(digit));
    }

    public static int fiboNumber(int n) {
        if (numbers.size() < n) {
            for (int i = numbers.size(); i <= n; i++) {
                numbers.add(numbers.get(i - 2) + numbers.get(i - 1));
            }
        }
        return numbers.get(n-1);
    }

    public static boolean isGoldenTriangle(int a, int b, int c) {
        boolean result = false;
        if (TriangleInfo.isIsoscelesTriangle(a, b, c)) {
            result = checkSide(a, b, c) || checkSide(b, c, a) || checkSide(c, a, b);
        }
        return result;
    }

    public static void main(String[] args) {
        Assert.assertTrue(isGoldenTriangle(34, 55, 55));
        Assert.assertTrue(isGoldenTriangle(89, 55, 89));
        Assert.assertFalse(containsDigit(123456789, 0));
        Assert.assertFalse(containsDigit(123456, 54));
        Assert.assertTrue(containsDigit(13579, 57));
        System.out.print("Перечисление первых 15 чисел Фибоначи:");
        for (int i = 0; i < 15; i++) {
            System.out.print(" " + fiboNumber(i+1));
        }
        System.out.print(String.format(".%s", System.lineSeparator()));
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            count = buildTriangolo(i) ? count + 1 : count;
        }
        System.out.println(String.format("Всего найдено %d золотых треугольников со стороной меньше 100.", count));
    }
}
