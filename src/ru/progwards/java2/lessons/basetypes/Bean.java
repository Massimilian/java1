package ru.progwards.java2.lessons.basetypes;

public class Bean<K extends HashValue, V> {
    private V value;
    private K key;

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public Bean(K key, V value) {
        this.value = value;
        this.key = (K) key;
    }
}
