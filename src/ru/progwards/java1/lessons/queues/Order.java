package ru.progwards.java1.lessons.queues;

/**
 * Class of orders to form order queue
 */
public class Order implements Comparable<Order> {
    private static int incr = 0;
    private final double sum;
    private final int num = ++incr;
    private int priority;

    public Order(double sum) {
        this.sum = sum;
        this.setPriority(sum);
    }

    public double getSum() {
        return sum;
    }

    public int getNum() {
        return num;
    }

    /**
     * Method for set priority (1, 2 or 3)
     *
     * @param sum for check priority
     */
    public void setPriority(double sum) {
        if (sum >= 20000.0) {
            this.priority = 3;
        } else if (sum < 10000.0) {
            this.priority = 1;
        } else {
            priority = 2;
        }
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Order o) {
        return o.getPriority() - this.getPriority() == 0 ? Integer.compare(this.getNum(), o.getNum()) : Integer.compare(o.getPriority(), this.getPriority());
    }

    @Override
    public String toString() {
        return String.format("Order №%d:%s sum: %f;%s number: %d;%s priority: %d.%s",
                this.num, System.lineSeparator(), this.sum, System.lineSeparator(), this.num, System.lineSeparator(),
                this.priority, System.lineSeparator());
    }
}
