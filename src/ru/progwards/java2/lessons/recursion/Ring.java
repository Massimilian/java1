package ru.progwards.java2.lessons.recursion;

public class Ring implements Comparable<Ring> {
    private int size;

    public Ring(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(Ring o) {
        return Integer.compare(this.size, o.size);
    }
}
