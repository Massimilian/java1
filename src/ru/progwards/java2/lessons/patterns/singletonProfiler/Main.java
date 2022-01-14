package ru.progwards.java2.lessons.patterns.singletonProfiler;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        UpgradedProfiler up = SingletonProfiler.getInstance();
        up.enterSection("one");
        up.enterSection("two");
        Thread.sleep(1000);
        up.exitSection("one");
        UpgradedProfiler newUp = SingletonProfiler.getInstance();
        assert newUp == up;
    }
}
