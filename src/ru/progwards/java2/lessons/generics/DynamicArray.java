package ru.progwards.java2.lessons.generics;

public class DynamicArray<T> {
    private int count = 0;
    private T[] ts = (T[]) new Object[1];

    public int getCount() {
        return count;
    }

    private void test(int value) {
        while (value >= ts.length) {
            T[] temp = (T[]) new Object[ts.length * 2];
            for (int i = 0; i < ts.length; i++) {
                temp[i] = ts[i];
            }
            ts = temp;
        }
    }

    public void add(T t) {
        while (ts[count] != null) {
            count++;
            this.test(count + 1);
        }
        this.test(count + 1);
        ts[count++] = t;
    }

    public void insert(int pos, T t) {
        this.test(pos);
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
