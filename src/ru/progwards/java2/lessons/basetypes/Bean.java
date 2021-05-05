package ru.progwards.java2.lessons.basetypes;

public class Bean<K extends Key, V> {
    private V value;
    private K key;

    public V getValue() {
        return value;
    }

    public Key getKey() {
        return key;
    }

    public Bean(Key key, V value) {
        this.value = value;
        this.key = (K) key;
    }
}
