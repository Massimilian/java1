package ru.progwards.java2.lessons.basetypes;

public class IntKey extends Key{
    private final int value;

    public IntKey(int value) {
        this.value = value;
    }

    @Override
    public int getHash() {
        return value;
    }
}
