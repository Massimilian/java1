package ru.progwards.java2.lessons.basetypes.doubleHashTable;

public class DoubleHashTableObject<T> implements HashValue {
    private int key;
    private final T value;

    public DoubleHashTableObject(T value) {
        this.value = value;
        this.key = this.getHash();
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int getHash() {
        int hash = 0;
        if (value instanceof Number) {
            hash = Math.abs(((Number) value).intValue());
        } else if (value instanceof CharSequence) {
            for (int i = 0; i < ((CharSequence) value).length(); i++) {
                hash += (int) ((CharSequence) value).charAt(i);
            }
        } else {
            hash = (int) Math.random()*Integer.MAX_VALUE;
        }
        return hash;
    }
}
