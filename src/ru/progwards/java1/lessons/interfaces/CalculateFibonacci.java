package ru.progwards.java1.lessons.interfaces;

import ru.progwards.java1.lessons.compare_if_cycles.CyclesGoldenFibo;

public class CalculateFibonacci {

    public static class CacheInfo {
        public int n;
        public int fibo;
    }

    private static CacheInfo lastFibo;

    public static int fiboNumber(int n) {
        if (lastFibo == null || lastFibo.n != n) {
            lastFibo = new CacheInfo();
            lastFibo.n = n;
            lastFibo.fibo = CyclesGoldenFibo.fiboNumber(n);
        }
        return lastFibo.fibo;
    }

    public static CacheInfo getLastFibo() {
        return lastFibo;
    }

    public static void clearLastFibo() {
        lastFibo = null;
    }
}
