package ru.progwards.java1.lessons.bitsworld;

import org.junit.Assert;
import static org.hamcrest.core.Is.is;

public class SumBits {
    public static int sumBits(byte value) {
        int result = 0;
// при работе с отрицательными данными не заполняет освободившиеся биты нолями,
// как говорилось в лекции, а заполняет единицами. Почему так?
//        while (value != 0) {
//            System.out.println(Integer.toBinaryString(value));
//            result += value & 1;
//            value >>>= 1;
//        }
        for (int i = 0; i < 32; i++) {
            result += value & 1;
            value >>>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        byte a = 0b1110110;
        byte b = 0b1101011;
        byte c = -1;
        Assert.assertEquals(sumBits(c), 32);
        Assert.assertEquals(sumBits(a), sumBits(b));
        Assert.assertThat(sumBits(a) == 5, is(true));
    }
}
