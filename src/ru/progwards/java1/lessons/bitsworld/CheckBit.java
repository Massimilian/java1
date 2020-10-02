package ru.progwards.java1.lessons.bitsworld;

import org.junit.Assert;

public class CheckBit {
    public static int checkBit(byte value, int bitNumber) {
        return 1 & (value >> bitNumber);
    }

    public static void main(String[] args) {
        Assert.assertEquals(checkBit((byte) 0b10, 1), 1);
        Assert.assertEquals(checkBit((byte) 0b100, 1), 0);
        Assert.assertEquals(checkBit((byte) 0b1001, 1), 0);
        Assert.assertEquals(checkBit((byte) 0b10010, 1), 1);

    }
}
