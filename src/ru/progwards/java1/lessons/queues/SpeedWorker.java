package ru.progwards.java1.lessons.queues;

/**
 * Class to save method and its speed
 */
public class SpeedWorker implements Comparable<SpeedWorker> {
    String name;
    long speed;

    public SpeedWorker(String name, long speed) {
        this.name = name;
        this.speed = speed;
    }

    @Override
    public int compareTo(SpeedWorker o) {
        return o.speed != this.speed ? Long.compare(this.speed, o.speed) : this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
