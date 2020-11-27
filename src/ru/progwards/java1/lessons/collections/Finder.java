package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Finder {

    /**
     * Method for find the min sum of two neighbors
     *
     * @param numbers Collection for check
     * @return members with min sum.
     */
    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        List<Integer> result;
        if (numbers.size() > 1) {
            int point = 0;
            long sum = Long.MAX_VALUE;
            ArrayList<Integer> collection = new ArrayList<>(numbers);
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (sum > collection.get(i) + collection.get(i + 1)) {
                    sum = collection.get(i) + collection.get(i + 1);
                    point = i;
                }
            }
            result = new ArrayList<>(Arrays.asList(point, point + 1));
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * Method to find local max values
     *
     * @param numbers Collection for check
     * @return Collection of local numbers
     */
    public static Collection<Integer> findLocalMax(Collection<Integer> numbers) {
        List<Integer> res = new ArrayList<>(numbers);
        List<Integer> result = new ArrayList<>();
        if (numbers.size() > 2) {
            for (int i = 1; i < res.size() - 1; i++) {
                if (res.get(i - 1) < res.get(i) && res.get(i + 1) < res.get(i)) {
                    result.add(res.get(i));
                }
            }
        }
        return result;
    }

    /**
     * Method to answer are the numbers between 1 and Collection size in the Collection
     *
     * @param numbers Collection to check
     * @return are the numbers there?
     */
    public static boolean findSequence(Collection<Integer> numbers) {
        boolean res = true;
        for (int i = 1; i < numbers.size(); i++) {
            if (!numbers.contains(i)) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * Method to find the max equal neightbor entries in the Collection
     *
     * @param names Collection for check
     * @return Value and number of its entries
     */
    public static String findSimilar(Collection<String> names) {
        String name = "";
        String finName = "No name";
        int count = 1;
        int finCount = 1;
        if (names.isEmpty()) {
            finCount = 0;
        } else {
            List<String> list = new ArrayList<>(names);
            finName = list.get(0);
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).equals(list.get(i + 1))) {
                    count++;
                    name = list.get(i);
                } else {
                    if (count > finCount) {
                        finCount = count;
                        finName = name;
                    }
                    count = 1;
                }
            }
        }
        return String.format("%s:%d", finName, finCount);
    }

    public static void main(String[] args) {
        List<Integer> tester = new ArrayList<>(Arrays.asList(4, 2, 3, 5, 1, 7, 0, 8, 1));
        List<String> strTester = new ArrayList<>(Arrays.asList("A", "a", "a", "B", "C", "a", "a", "a", "c"));
        List<String> strTesterTwo = new ArrayList<>(Arrays.asList("A", "A", "A", "a", "a", "B", "C", "C", "C", "C", "a", "a", "a", "c"));
        assert Finder.findMinSumPair(tester).size() == 2;
        assert ((List<Integer>) Finder.findMinSumPair(tester)).get(0) == 1;
        assert ((List<Integer>) Finder.findMinSumPair(tester)).get(1) == 2;
        assert ((List<Integer>) Finder.findLocalMax(tester)).get(0) == 5;
        assert ((List<Integer>) Finder.findLocalMax(tester)).get(1) == 7;
        assert ((List<Integer>) Finder.findLocalMax(tester)).get(2) == 8;
        assert !Finder.findSequence(tester);
        assert Finder.findSequence(new ArrayList<>());
        assert Finder.findSimilar(strTester).equals("a:3");
        assert Finder.findSimilar(strTesterTwo).equals("C:4");
    }
}
