package ru.progwards.java2.lessons.basetypes;

public class Bean<V> {
    private V value;
    private int key;

    public V getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

    public Bean(int key, V value) {
        this.value = value;
        this.key = key;
    }
}
