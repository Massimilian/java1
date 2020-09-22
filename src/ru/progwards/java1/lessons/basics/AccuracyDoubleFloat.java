package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {
//    private final double PI = Math.PI;

    private static final double PI = 3.14;
    private static final double EARTH_RADIUS = 6_371.2;

    public static double volumeBallDouble(double radius) {
        return 4/3*PI*Math.pow(radius, 3);
    }

    public static float volumeBallFloat(float radius) {
        return 4f/3f*(float)PI*(float)Math.pow(radius, 3);
    }

    public static double calculateAccuracy(double radius) {
        return volumeBallDouble(radius) - volumeBallDouble((float)radius);
    }

    public static void main(String[] args) {
        System.out.println("Разница при вычислении объёма земли составляет:");
        System.out.println(calculateAccuracy(EARTH_RADIUS));
    }
}
