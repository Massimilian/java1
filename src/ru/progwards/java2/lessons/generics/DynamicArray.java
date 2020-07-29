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
            if (ts.length == count) {
                this.test(count + 1);
            }
        }
        this.test(count + 1);
        ts[count++] = t;
    }

    public void insert(int pos, T t) {
        if (ts.length <= pos) {
            this.test(pos);
        } else {
            this.plusOne(pos);
        }
        ts[pos] = t;
    }

    public void remove(int pos) {
        if (pos <= count) {
            if (ts.length == 1 && pos == 0) { // во избежание нолевого массива
                ts[pos] = null;
            } else {
                this.minusOne(pos);
            }
        }
    }

    private void plusOne(int pos) {
        T[] tsNew = (T[]) new Object[ts.length + 1];
        for (int i = 0; i < tsNew.length; i++) {
            if (i < pos) {
                tsNew[i] = this.ts[i];
            } else if (i == pos) {
                continue;
            } else {
                tsNew[i] = this.ts[i - 1];
            }
        }
        ts = tsNew;
    }

    private void minusOne(int pos) {
        T[] tsNew = (T[]) new Object[ts.length - 1];
        for (int i = 0; i < ts.length; i++) {
            if (i < pos) {
                tsNew[i] = this.ts[i];
            } else if (i == pos) {
                continue;
            } else {
                tsNew[i - 1] = this.ts[i];
            }
        }
        ts = tsNew;
        count = 0;
    }

    public T get(int pos) {
        return pos <= count ? ts[pos] : null;
    }

    public int getSize() {
        return ts.length;
    }
}
