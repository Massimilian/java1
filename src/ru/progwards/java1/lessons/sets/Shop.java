package ru.progwards.java1.lessons.sets;

import java.util.List;

/**
 * Class of Shop with products
 */
public class Shop {
    private final List<Product> products;

    public Shop(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
