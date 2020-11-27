package ru.progwards.java1.lessons.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIterator<T> implements Iterator<T> {

    private final ArrayIterator[] iterators;
    private int point = 0;
    private final int max;
    private ArrayIterator<T> currentIterator;


    MatrixIterator(T[][] array) {
        this.iterators = this.prepareIterators(array);
        this.max = array.length;
        this.currentIterator = this.iterators.length == 0 ? null : this.iterators[0];
    }

    /**
     * Method for prepare an array of iterators for matrix
     *
     * @param array for get the size of inner arrays
     * @return array of iterators
     */
    private ArrayIterator[] prepareIterators(T[][] array) {
        ArrayIterator[] iterators = new ArrayIterator[array.length];
        for (int i = 0; i < array.length; i++) {
            iterators[i] = new ArrayIterator<>(array[i]);
        }
        return iterators;
    }

    /**
     * Method to check that we have one more step for iterator or one more iterator
     *
     * @return Info about possibility to move more
     */
    @Override
    public boolean hasNext() {
        return currentIterator.hasNext() || this.hasMoreIterator();
    }

    /**
     * Inner method to check that we have one more iterator
     *
     * @return info about that we have an iterator
     */
    private boolean hasMoreIterator() {
        boolean result = false;
        if (point < max - 1) {
            this.currentIterator = iterators[++point];
            result = true;
        }
        return result;
    }

    /**
     * Method to move on a next step
     *
     * @return next value
     * @throws NoSuchElementException
     */
    @Override
    public T next() throws NoSuchElementException {
        if (hasNext()) {
            return currentIterator.next();
        } else {
            throw new NoSuchElementException("Out of bounds.");
        }
    }

    public static void main(String[] args) {
        Integer[][] matrix = new Integer[][]{{1, 2, 3}, {4, 5, 6, 7}, {8, 9}};
        MatrixIterator<Integer> mi = new MatrixIterator<>(matrix);
        Integer i = 1;
        while (mi.hasNext()) {
            Integer value = mi.next();
            assert value == i++;
        }
        String[][] words = new String[][]{{"A", "B", "C"}, {"D", "E", "F", "G", "H"}, {"I", "J", "K"}, {"L"}};
        MatrixIterator<String> miTwo = new MatrixIterator<>(words);
        StringBuilder sb = new StringBuilder();
        while (miTwo.hasNext()) {
            sb.append(miTwo.next());
        }
        assert sb.toString().equals("ABCDEFGHIJKL");
    }
}
