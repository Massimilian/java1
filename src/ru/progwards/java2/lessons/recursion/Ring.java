package ru.progwards.java2.lessons.recursion;

/**
 * Ring class for Hanoi Tower work
 */
public class Ring implements Comparable<Ring> {
    private final int size;

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

    /**
     * Method for prepare the name of ring
     *
     * @return prepared name
     */
    public String getName() {
        return size < 10 ? "00" + size : size < 100 ? "0" + size : String.valueOf(size);
    }
}
