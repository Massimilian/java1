package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Creator {

    /**
     * Method for prepare Collection with Even Increasing Numbers
     *
     * @param n the size of collection
     * @return prepared collection
     */
    public static Collection<Integer> fillEven(int n) {
        Collection<Integer> result = new ArrayList<>();
        int point = 0;
        for (int i = 0; i < n; i++) {
            result.add(point += 2);
        }
        return result;
    }

    /**
     * Method for prepare Collection with Odd Decreasing Numbers
     *
     * @param n the size of collection
     * @return prepared collection
     */
    public static Collection<Integer> fillOdd(int n) {
        Collection<Integer> result = new LinkedList<>();
        int point = n * 2 + 1;
        for (int i = 0; i < n; i++) {
            result.add(point -= 2);
        }
        return result;
    }

    /**
     * Method for prepare collection with number, square and cube values
     *
     * @param n number of values
     * @return prepared collection
     */
    public static Collection<Integer> fill3(int n) {
        Collection<Integer> result = new ArrayList<>();
        for (int i = 0; i < n * 3; i += 3) {
            result.add(i);
            result.add(i * i);
            result.add(i * i * i);
        }
        return result;
    }

    public static void main(String[] args) {
        assert Creator.fillEven(10).size() == 10;
        assert Creator.fillEven(5).contains(10);
        assert Creator.fillEven(0).isEmpty();
        assert Creator.fillOdd(10).size() == 10;
        assert ((List<Integer>) Creator.fillOdd(5)).get(1) == 7;
        assert ((List<Integer>) Creator.fillOdd(5)).get(3) == 3;
        assert Creator.fill3(10).size() == 10 * 3;
        assert Creator.fill3(0).isEmpty();
        assert ((List<Integer>) Creator.fill3(2)).get(4) == 9;
    }
}
