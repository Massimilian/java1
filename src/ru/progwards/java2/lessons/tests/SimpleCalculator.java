package ru.progwards.java2.lessons.tests;

public class SimpleCalculator {
    public int sum(int val1, int val2) {
        if (((long) val1) + val2 > Integer.MAX_VALUE || ((long) val1) + val2 < Integer.MIN_VALUE) {
            throw new ArithmeticException("Out of integer value");
        }
        return val1 + val2;
    }


    public int diff(int val1, int val2) {
        if (((long) val1) - val2 > Integer.MAX_VALUE || ((long) val1) - val2 < Integer.MIN_VALUE) {
            throw new ArithmeticException("Out of integer value");
        }
        return val1 - val2;
    }

    public int mult(int val1, int val2) {
        if (((long) val1) * val2 > Integer.MAX_VALUE || ((long) val1) * val2 < Integer.MIN_VALUE) {
            throw new ArithmeticException("Out of integer value");
        }
        return val1 * val2;
    }

    public int div(int val1, int val2) {
        if (val2 == 0) {
            throw new ArithmeticException("Cannot divide at 0");
        }
        System.out.println("Attention! The value may be not сorrect.");
        return val1 / val2;
    }

    public double correctDiv(int val1, int val2) {
        if (val2 == 0) {
            throw new ArithmeticException("Cannot divide at 0");
        }
        if (((long) val1) / val2 > Integer.MAX_VALUE || ((long) val1) / val2 < Integer.MIN_VALUE) {
            throw new ArithmeticException("Out of integer value");
        }
        return val1 / (double)val2;
    }
}
