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
    public void add(Order order) {
        orders.offer(order);
    }

    public static void main(String[] args) {
        OrderQueue oq = new OrderQueue();
        oq.add(new Order(26790));
        oq.add(new Order(13399));
        oq.add(new Order(17294));
        oq.add(new Order(29922));
        oq.add(new Order(1121));
        oq.add(new Order(8362));
        oq.add(new Order(19821));
        oq.add(new Order(2861));
        oq.add(new Order(24605));
        oq.add(new Order(928));
        oq.add(new Order(26959));
        oq.add(new Order(29001));
        oq.add(new Order(19558));
        oq.add(new Order(28162));
        oq.add(new Order(418));
        oq.add(new Order(28143));
        oq.add(new Order(14675));
        oq.add(new Order(15905));
        oq.add(new Order(25803));
        oq.add(new Order(5445));
        oq.add(new Order(12131));
        oq.add(new Order(17927));
        oq.add(new Order(6889));
        oq.add(new Order(16118));
        for (int i = 0; i < 24; i++) {
            System.out.println(oq.get());
        }
    }
}
