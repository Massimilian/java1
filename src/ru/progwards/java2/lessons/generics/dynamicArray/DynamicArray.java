package ru.progwards.java2.lessons.generics.dynamicArray;

public class DynamicArray<T> {
    private int count = 0;
    private T[] ts = (T[]) new Object[1];

    public int getCount() {
        return count;
    }

    private void test() {
        if (count == ts.length) {
            T[] temp = (T[]) new Object[count * 2];
            for (int i = 0; i < count; i++) {
                temp[i] = ts[i];
            }
            ts = temp;
        }
    }

    public void add(T t) {
        this.test();
        ts[count++] = t;
    }

    public void insert(T t, int pos) {
        this.test();
        for (int i = count; i > pos; i--) {
            ts[i] = ts[i - 1];
        }
        ts[pos] = t;
    }

    public void remove(int pos) {
        if (pos <= count) {
            ts[pos] = null;
        }
    }

    public T get(int pos) {
        return pos <= count ? ts[pos] : null;
    }

    public int getSize() {
        return ts.length;
    }
}
