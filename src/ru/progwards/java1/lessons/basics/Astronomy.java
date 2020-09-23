package ru.progwards.java1.lessons.basics;

public class Astronomy {
//    private final double PI = Math.PI;

    private static final double PI = 3.14;
    private static final double EARTH_RADIUS = 6_371.2;
    private static final double MERCURY_RADIUS = 2_439.7;
    private static final double JUPITER_RADIUS = 71_492;


    public static Double sphereSquare(Double r) {
        return 4 * PI * Math.pow(r, 2);
    }

    public static Double earthSquare() {
        return sphereSquare(EARTH_RADIUS);
    }

    public static Double mercurySquare() {
        return sphereSquare(MERCURY_RADIUS);
    }

    public static Double jupiterSquare() {
        return sphereSquare(JUPITER_RADIUS);
    }

    public static Double earthVsMercury() {
        return sphereSquare(EARTH_RADIUS) / sphereSquare(MERCURY_RADIUS);
    }

    public static Double earthVsJupiter() {
        return sphereSquare(JUPITER_RADIUS) / sphereSquare(EARTH_RADIUS);
    }

    public static void info() {
        String separator = System.lineSeparator();
        System.out.println(String.format("Информация о планетах:%s1. площадь Земли: %f квадратных км.;%s2. площадь Меркурия: %f квадратных км.;%s3. площадь Юпитера %f квадратных км.;%s4. Земля больше Меркурия в %f раз;%s5. Земля меньше Юпитера в %f раз; %s Программа окончила свою работу.",
                separator, earthSquare(), separator, mercurySquare(), separator, jupiterSquare(), separator, earthVsMercury(), separator, earthVsJupiter(), separator));

    }

    public static void main(String[] args) {
        info();
    }
}
