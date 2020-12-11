package ru.progwards.java1.lessons.maps;

/**
 * Special class for save goods
 */
public class Good {
    private final String owner;
    private final String name;
    private final int count;
    private final double sum;

    public Good(String owner, String name, int count, double sum) {
        this.owner = owner;
        this.name = name;
        this.count = count;
        this.sum = sum;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", owner, name, count, sum);
    }
}
