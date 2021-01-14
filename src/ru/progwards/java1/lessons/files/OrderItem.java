package ru.progwards.java1.lessons.files;

/**
 * Class of Order item (part of Order)
 */
public class OrderItem {
    public String googsName;
    public int count;
    public double price;

    public OrderItem(String googsName, int count, double price) {
        this.googsName = googsName;
        this.count = count;
        this.price = price;
    }

    public OrderItem() {
    }
}
