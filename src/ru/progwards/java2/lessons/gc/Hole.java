package ru.progwards.java2.lessons.gc;

import java.util.Comparator;
import java.util.Objects;

/**
 * Hole class for Heap2
 */
public class Hole implements Comparator<Hole> {
    private int size;
    private int position;

    public Hole(int size, int position) {
        this.size = size;
        this.position = position;
    }

    public Hole(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void incrSize() {
        this.size++;
    }

    @Override
    public int compare(Hole o1, Hole o2) {
        return Integer.compare(o1.getPosition(), o2.getPosition());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hole hole = (Hole) o;
        return position == hole.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
