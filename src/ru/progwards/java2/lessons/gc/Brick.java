package ru.progwards.java2.lessons.gc;

public class Brick implements CounterForCompare {
    int size;

    public Brick(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int compareTo(CounterForCompare o) {
        return Integer.compare(this.size, o.getCounter());
    }

    @Override
    public int getCounter() {
        return this.size;
    }
}
