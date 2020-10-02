package ru.progwards.java1.lessons.bitsworld;

import org.junit.Assert;

public class Binary {
    private int num;

    public Binary(int num) {
        this.num = num;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(num % 2);
            num >>= 1;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        Assert.assertEquals(new Binary(0b00001101).toString(), "00001101");
        Assert.assertEquals(new Binary(0b01010101).toString(), "01010101");
    }
}
