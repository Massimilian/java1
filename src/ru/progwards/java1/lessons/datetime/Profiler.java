package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Profiler {
    static ArrayList<StatisticInfo> stats = new ArrayList<>();
    private static final ArrayDeque<StatisticInfo> inWork = new ArrayDeque<>();

    /**
     * Method to fix the time of entering into the section
     *
     * @param name of section
     */
    public static void enterSection(String name) {
        StatisticInfo si = new StatisticInfo(name);
        inWork.add(si);
    }

    /**
     * Method to fix the time of exiting from the section
     *
     * @param name of section
     */
    public static void exitSection(String name) {
        StatisticInfo si = inWork.pollLast();
        Duration duration = Duration.between(si.start, Instant.now());
        si.fullTime = (int) (duration.getNano() / 1000000 + duration.getSeconds() * 1000);
        si.selfTime += si.fullTime;
        if (!inWork.isEmpty()) {
            StatisticInfo temp = inWork.peekLast();
            if (temp.open) {
                temp.selfTime -= si.fullTime;
            }
        }
        si.open = false;
        stats.add(si);
    }

    /**
     * Method for preparing and sorting info about StatisticInfos
     *
     * @return prepared information
     */
    public static List<StatisticInfo> getStatisticInfo() {
        return prepareInfo();
    }

    /**
     * Method for clean, collapse and sort StatisticIbfos.
     *
     * @return prepares ArrayList with StatisticInfos
     */
    private static ArrayList<StatisticInfo> prepareInfo() {
        ArrayList<StatisticInfo> result = new ArrayList<>();
        while (!stats.isEmpty()) {
            StatisticInfo si = stats.remove(0);
            while (stats.contains(si)) {
                StatisticInfo plus = stats.remove(stats.indexOf(si));
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

    public static void main(String[] args) throws InterruptedException {
        enterSection("1");
        Thread.sleep(10);
        enterSection("2");
        Thread.sleep(10);
        for (int i = 0; i < 300; i++) {
            enterSection("3");
            Thread.sleep(1);
            exitSection("3");
        }
        exitSection("2");
        exitSection("1");
        ArrayList<StatisticInfo> tester = (ArrayList<StatisticInfo>) getStatisticInfo();
        assert tester.get(2).sectionName.equals("3");
        assert tester.get(2).count == 300;
        assert tester.get(2).fullTime == tester.get(2).selfTime;
        assert tester.get(2).fullTime >= 300;
        assert tester.get(1).fullTime == tester.get(1).selfTime + tester.get(2).fullTime;
        assert tester.get(1).count == 1;
        assert tester.get(0).count == tester.get(1).count;
        assert tester.get(0).fullTime == tester.get(0).selfTime + tester.get(1).fullTime;
    }
}
