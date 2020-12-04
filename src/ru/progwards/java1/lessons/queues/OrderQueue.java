package ru.progwards.java1.lessons.queues;

import java.util.PriorityQueue;

/**
 * Class for work with order's queue
 */
public class OrderQueue {
    private final PriorityQueue<Order> orders = new PriorityQueue<>();

    /**
     * Method to get the most important order
     *
     * @return order
     */
    public Order get() {
        return orders.poll();
    }

    /**
     * Method to set a new order
     *
     * @param order
     */
    public void set(Order order) {
        orders.offer(order);
    }

    public static void main(String[] args) {
        Order ordBigOne = new Order(100000);
        Order ordSmallOne = new Order(1000);
        Order ordSmallTwo = new Order(2000);
        Order ordMediumOne = new Order(10000);
        Order ordMediumTwo = new Order(25000);
        Order ordMediumThree = new Order(20000);
        OrderQueue oq = new OrderQueue();
        oq.set(ordMediumThree);
        oq.set(ordSmallOne);
        oq.set(ordMediumOne);
        oq.set(ordBigOne);
        oq.set(ordSmallTwo);
        oq.set(ordMediumTwo);
        assert oq.get().equals(ordBigOne);
        assert oq.get().equals(ordMediumOne);
        assert oq.get().equals(ordMediumTwo);
        assert oq.get().equals(ordMediumThree);
        assert oq.get().equals(ordSmallOne);
        assert oq.get().equals(ordSmallTwo);
    }
}
