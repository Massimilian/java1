package ru.progwards.java2.lessons.basetypes;

import java.util.Objects;

/**
 * Class for keep the key value in String format
 */
public class StringKey extends Key {
    private final String value;

    public StringKey(String value) {
        this.value = value;
    }

    @Override
    public int getHash() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringKey stringKey = (StringKey) o;
        return Objects.equals(value, stringKey.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
