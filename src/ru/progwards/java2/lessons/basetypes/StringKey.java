package ru.progwards.java2.lessons.basetypes;

public class StringKey extends Key{
    private final String value;

    public StringKey(String value) {
        this.value = value;
    }

    @Override
    public int getHash() {
        return value.hashCode();
    }
}
