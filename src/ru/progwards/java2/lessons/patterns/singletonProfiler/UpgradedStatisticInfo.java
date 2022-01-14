package ru.progwards.java2.lessons.patterns.singletonProfiler;

import java.time.Instant;
import java.util.Objects;

/**
 * Copy of StatisticInfo
 */
public class UpgradedStatisticInfo {
    public String sectionName;
    public int fullTime;
    public int selfTime;
    public int count;
    public Instant start;
    public boolean open;

    public UpgradedStatisticInfo(String sectionName) {
        this.sectionName = sectionName;
        this.start = Instant.now();
        this.selfTime = 0;
        this.open = true;
        this.count = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpgradedStatisticInfo that = (UpgradedStatisticInfo) o;
        return Objects.equals(sectionName, that.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionName);
    }
}
