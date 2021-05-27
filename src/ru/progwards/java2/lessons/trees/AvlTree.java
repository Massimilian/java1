package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;

public class AvlTree<K extends Comparable<K>, V> extends BinaryTree {
    public void put(AvlTreeLeaf<K, V> atl) throws Exception {
        this.put(atl.getKey(), atl.getValue());
    }

    public void put(K key, V value) throws Exception {
        AvlTreeLeaf atl = new AvlTreeLeaf(key, value);
        this.add(atl);
        if (!atl.checkBalance()) {
            balance(atl);
        }
    }

    public void balance(AvlTreeLeaf v) {
        System.out.println("balance action");
        AvlTreeLeaf y = (AvlTreeLeaf) v.parent.parent.parent;
        AvlTreeLeaf x = (AvlTreeLeaf) v.parent.parent;
        AvlTreeLeaf a = (AvlTreeLeaf) v.parent;
        AvlTreeLeaf b = (AvlTreeLeaf) x.getRight(); // can be null
        AvlTreeLeaf c = (AvlTreeLeaf) y.getRight(); // can be null
        if (y.getBalance() == -2 && (b == null || (b.getLeft() == null && b.getRight() == null))) {
            System.out.println("Small right turn");
        }
    }

    public static void main(String[] args) throws Exception {
        AvlTree<Integer, String> at = new AvlTree<>();
        AvlTreeLeaf atl3 = new AvlTreeLeaf(3, "Three");
        at.put(atl3);
        at.put(1, "One");
        at.put(5, "Five");
        at.put(2, "Two");
        at.put(0, "Zero");
        at.put(-1, "Minus one");
//        at.put(7, "Seven");
//        at.put(9, "Nine");
        System.out.println();
    }
}
