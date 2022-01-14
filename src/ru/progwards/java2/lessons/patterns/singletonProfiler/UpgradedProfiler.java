package ru.progwards.java2.lessons.patterns.singletonProfiler;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Updated thread-safe Profiler
 */
public class UpgradedProfiler {
    private CopyOnWriteArrayList<UpgradedStatisticInfo> stats = new CopyOnWriteArrayList<>();
    private final ArrayDeque<UpgradedStatisticInfo> inWork = new ArrayDeque<>();
    ReentrantLock inWorkLock = new ReentrantLock();

    /**
     * Method for enter into the section
     *
     * @param name is a sectionName
     */
    public void enterSection(String name) {
        UpgradedStatisticInfo si = new UpgradedStatisticInfo(name);
        inWork.add(si);
    }

    /**
     * Method for thread-safe exit from the last section
     *
     * @param name is a sectionName
     */
    public void exitSection(String name) {
        inWorkLock.tryLock();
        String elementName;
        do {
            elementName = inWork.peekLast().sectionName;
            UpgradedStatisticInfo si = inWork.pollLast();
            Duration duration = Duration.between(si.start, Instant.now());
            si.fullTime = (int) (duration.getNano() / 1000000 + duration.getSeconds() * 1000);
            si.selfTime += si.fullTime;
            if (!inWork.isEmpty()) {
                UpgradedStatisticInfo temp = inWork.peekLast();
                if (temp.open) {
                    temp.selfTime -= si.fullTime;
                }
            }
            si.open = false;
            stats.add(si);
        } while (!elementName.equals(name));
        inWorkLock.unlock();
    }

    /**
     * Method for get the thread-safe list of current sections
     *
     * @return sections
     */
    private List<UpgradedStatisticInfo> getUpgradedStatisticInfo() {
        CopyOnWriteArrayList<UpgradedStatisticInfo> result = new CopyOnWriteArrayList<>();
        while (!stats.isEmpty()) {
            UpgradedStatisticInfo si = stats.remove(0);
            while (stats.contains(si)) {
                UpgradedStatisticInfo plus = stats.remove(stats.indexOf(si));
                si.selfTime += plus.selfTime;
                si.fullTime += plus.fullTime;
                si.count++;
            }
            result.add(si);
        }
        result.sort(Comparator.comparing(o -> o.sectionName));
        stats = result;
        return result;
    }
}