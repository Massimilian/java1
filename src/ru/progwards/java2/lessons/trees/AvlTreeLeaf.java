package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;

public class AvlTreeLeaf<K extends Comparable<K>, V> extends TreeLeaf<K, V> {
    private int balance = 0;

    public AvlTreeLeaf(K key, V value) {
        super(key, value);
    }

    public boolean checkBalance() {
        if (this.parent != null) {
            if (this.parent.getRight() == this) {
                if (this.parent.getLeft() == null) {
                    this.parent.incrBalance();
                }
            } else {
                if (this.parent.getRight() == null) {
                    this.parent.decrBalance();
                }
            }
            return Math.abs(this.getBalance()) < 2 && ((AvlTreeLeaf) parent).checkBalance();
        } else {
            return Math.abs(this.getBalance()) < 2;
        }
    }


}