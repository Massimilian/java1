package ru.progwards.java1.lessons.bitsworld;

import org.junit.Assert;

import static org.hamcrest.core.Is.is;

public class SumBits {
    public static int sumBits(byte value) {
        int result = 0;
        while (value != 0) {
            result += value & 1;
            value >>>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        byte a = 0b1110110;
        byte b = 0b1101011;
        Assert.assertEquals(sumBits(a), sumBits(b));
        Assert.assertThat(sumBits(a) == 5, is(true));
    }
}
