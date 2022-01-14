package ru.progwards.java2.lessons.patterns.singletonProfiler;

/**
 * Singleton for UpdatedProfiler
 */
public class SingletonProfiler {
    private static volatile UpgradedProfiler instance;

    public static UpgradedProfiler getInstance() {
        if (instance == null) {
            synchronized (UpgradedProfiler.class) {
                if (instance == null) {
                    instance = new UpgradedProfiler();
                }
            }
        }
        return instance;
    }

    private SingletonProfiler() {
    }
}
