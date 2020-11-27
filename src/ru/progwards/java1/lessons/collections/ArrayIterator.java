package ru.progwards.java1.lessons.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    private int point = 0;
    private int max;


    ArrayIterator(T[] array) {
        this.array = array;
        this.max = array.length;
    }

    @Override
    public boolean hasNext() {
        return this.point < this.max;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (this.hasNext()) {
            return this.array[point++];
        } else {
            throw new NoSuchElementException("Out of bounds.");
        }
    }

    public static void main(String[] args) {
        Integer[] ints = new Integer[]{0, 1, 2, 3, 4, 5};
        ArrayIterator<Integer> ai = new ArrayIterator<>(ints);
        int pos = 0;
        while(ai.hasNext()) {
            int res = ai.next();
            assert res == pos++;
        }
    }
}
