package ru.progwards.java2.lessons.recursion;

import java.time.Instant;

/**
 * Special class of goods
 */
public class Goods {
    String name;
    String number;
    int available;
    double price;
    Instant expired;

    public Goods(String name, String number, int available, double price, Instant expired) {
        this.name = name;
        this.number = number;
        this.available = available;
        this.price = price;
        this.expired = expired;
    }
}