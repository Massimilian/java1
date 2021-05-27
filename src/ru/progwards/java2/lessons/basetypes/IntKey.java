package ru.progwards.java2.lessons.basetypes;

import java.util.Objects;

/**
 * Class for keep the key value in Integer format
 */
public class IntKey implements HashValue {
    private final int value;

    public IntKey(int value) {
        this.value = value;
    }

    @Override
    public int getHash() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntKey intKey = (IntKey) o;
        return value == intKey.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
