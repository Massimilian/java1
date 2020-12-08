package ru.progwards.java1.lessons.sets;

import java.util.*;

/**
 * Main analytics class
 */
public class ProductAnalytics {
    private final List<Shop> shops;
    private final List<Product> products;

    public ProductAnalytics(List<Product> products, List<Shop> shops) {
        this.products = products;
        this.shops = shops;
    }

    /**
     * Method returns products that exist in all the shops
     *
     * @return Set of products
     */
    public Set<Product> existInAll() {
        HashSet<Product> prods = new HashSet<>(products);
        for (Shop shop : shops) {
            prods.retainAll(shop.getProducts());
        }
        return prods;
    }

    /**
     * Method returns products that may be al least in one shop
     *
     * @return Set of products
     */
    public Set<Product> existAtListInOne() {
        HashSet<Product> prods = new HashSet<>();
        for (Shop shop : shops) {
            prods.addAll(shop.getProducts());
        }
        return prods;
    }

    /**
     * Method returns products that aren't in any shop
     *
     * @return Set of products
     */
    public Set<Product> notExistInShops() {
        HashSet<Product> prods = new HashSet<>(products);
        for (Shop shop : shops) {
            prods.removeAll(shop.getProducts());
        }
        return prods;
    }

    /**
     * Method returns products that are only in one shop
     *
     * @return Set of products
     */
    public Set<Product> existOnlyInOne() {
        HashSet<Product> prods = new HashSet<>();
        HashSet<Product> doubles = new HashSet<>();
        for (Shop shop : shops) {
            HashSet<Product> shopSet = new HashSet<>(shop.getProducts());
            shopSet.retainAll(prods);
            doubles.addAll(shopSet);
            prods.addAll(shop.getProducts());
        }
        prods.removeAll(doubles);
        return prods;
    }

    public static void main(String[] args) {
        Product pZero = new Product("Zero");
        Product pOne = new Product("One");
        Product pTwo = new Product("Two");
        Product pThree = new Product("Three");
        Product pFour = new Product("Four");
        Product pFive = new Product("Five");
        List<Product> lOne = new ArrayList<>(Arrays.asList(pZero, pOne, pTwo));
        List<Product> lTwo = new ArrayList<>(Arrays.asList(pZero, pTwo, pFour));
        List<Product> lThree = new ArrayList<>(Arrays.asList(pZero, pFive));
        Shop sOne = new Shop(lOne);
        Shop sTwo = new Shop(lTwo);
        Shop sThree = new Shop(lThree);
        ProductAnalytics pa = new ProductAnalytics(new ArrayList<>(Arrays.asList(pZero, pOne, pTwo, pThree, pFour, pFive)), new ArrayList<>(Arrays.asList(sOne, sTwo, sThree)));
        assert pa.existInAll().equals(new HashSet<>(List.of(pZero)));
        assert pa.existAtListInOne().equals(new HashSet<>(List.of(pZero, pOne, pTwo, pFour, pFive)));
        assert pa.existOnlyInOne().equals(new HashSet<>(List.of(pOne, pFour, pFive)));
        assert pa.notExistInShops().equals(new HashSet<>(List.of(pThree)));
    }
}
