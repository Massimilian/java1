package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.Iterator;

public class TreeIterator<K extends Comparable<K>, V> implements Iterator {
    ArrayList<V> list;
    private int maxValue;
    private int count = 0;

    public TreeIterator(TreeLeaf<K, V> leaf) {
        this.list = prepare(leaf);
        this.maxValue = this.list.size();
    }

    public ArrayList<V> prepare(TreeLeaf<K, V> leaf) {
        ArrayList<V> list = new ArrayList<>();
        write(leaf, list);
        return list;
    }

    private void write(TreeLeaf<K, V> leaf, ArrayList<V> list) {
        if (!list.contains(leaf.getValue())) {
            list.add(leaf.getValue());
        }
        if (leaf.getLeft() != null) {
            write(leaf.getLeft(), list);
        }
        if (leaf.getRight() != null) {
            write(leaf.getRight(), list);
        }
    }

    @Override
    public boolean hasNext() {
        return this.count != this.maxValue;
    }

    @Override
    public Object next() {
        return list.get(count++);
    }
}
