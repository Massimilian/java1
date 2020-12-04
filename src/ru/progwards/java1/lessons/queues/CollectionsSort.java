package ru.progwards.java1.lessons.queues;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Class for show different ways of collection's sort
 */
public class CollectionsSort {

    /**
     * Method for easy sort
     *
     * @param data is collection for sort
     */
    public static void mySort(Collection<Integer> data) {
        ArrayList<Integer> list = new ArrayList<>(data);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    int temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        data.clear();
        data.addAll(list);
    }

    /**
     * Method for sort by minimum values
     *
     * @param data is collection for sort
     */
    public static void minSort(Collection<Integer> data) {
        List<Integer> list = new ArrayList<>();
        int ceil = data.size();
        for (int i = 0; i < ceil; i++) {
            list.add(Collections.min(data));
            data.remove(Collections.min(data));
        }
        data.clear();
        data.addAll(list);
    }

    /**
     * Method for sort by method of Collections class
     *
     * @param data is collection for sort
     */
    public static void collSort(Collection<Integer> data) {
        List<Integer> list = new ArrayList<>(data);
        Collections.sort(list);
        data.clear();
        data.addAll(list);
    }

    /**
     * Method for sort all the sort methods by speed
     *
     * @return collection with name, sorted by speed
     */
    public static Collection<String> compareSort() {
        SpeedWorker mySort = new SpeedWorker("mySort", getSpeed("mySort"));
        SpeedWorker minSort = new SpeedWorker("minSort", getSpeed("minSort"));
        SpeedWorker collSort = new SpeedWorker("collSort", getSpeed("collSort"));
        TreeSet<SpeedWorker> speedworkers = new TreeSet<>(Arrays.asList(mySort, minSort, collSort));
        LinkedHashSet<String> result = new LinkedHashSet<>();
        while (!speedworkers.isEmpty()) {
            result.add(speedworkers.pollFirst().toString());
        }
        return result;
    }

    /**
     * Method for test speed of all the methods
     *
     * @param testedMethod is a name of the method for test
     * @return speed in nanos
     */
    private static long getSpeed(String testedMethod) {
        List<Integer> arr = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            arr.add(r.nextInt(Integer.MAX_VALUE));
        }
        Instant start;
        Instant end;

        switch (testedMethod) {
            case "mySort":
                start = Instant.now();
                mySort(arr);
                end = Instant.now();
                break;
            case "minSort":
                start = Instant.now();
                minSort(arr);
                end = Instant.now();
                break;
            case "collSort":
                start = Instant.now();
                collSort(arr);
                end = Instant.now();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + testedMethod);
        }
        return Duration.between(start, end).getNano();
    }

    public static void main(String[] args) {
        List<Integer> listForMySort = new ArrayList<>(Arrays.asList(1, 4, 5, 2, 3));
        List<Integer> listForMinSort = new ArrayList<>(listForMySort);
        List<Integer> listForSort = new ArrayList<>(listForMySort);
        List<Integer> result = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        CollectionsSort.mySort(listForMySort);
        CollectionsSort.minSort(listForMinSort);
        CollectionsSort.collSort(listForSort);
        assert listForMySort.equals(result);
        assert listForMinSort.equals(result);
        assert listForSort.equals(result);
        Collection<String> collection = CollectionsSort.compareSort();
    }
}
