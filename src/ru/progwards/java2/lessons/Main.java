package ru.progwards.java2.lessons;

import java.util.Comparator;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        BTree<Integer, Integer> tree = new BTree<>();
        tree.add(0, 0);
        tree.add(1, 1);
        tree.add(2, 2);
    }
}
