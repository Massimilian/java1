package ru.progwards.java1.lessons.bigints;

import org.junit.Assert;

import java.math.BigInteger;
import java.util.Arrays;

public class ArrayInteger {
    private final byte[] digits;

    public ArrayInteger(int n) {
        this.digits = new byte[n];
        this.fillIt(this.digits);
    }

    private void fillIt(byte[] digits) {
        Arrays.fill(digits, (byte) 0);
    }

    public byte[] getDigits() {
        return digits;
    }

    void fromInt(BigInteger value) {
        String val = value.toString();
        int count = this.digits.length - 1;
        for (int i = val.length() - 1; i >= 0; i--) {
            digits[count--] = Byte.parseByte(String.valueOf(val.charAt(i)));
        }
    }

    BigInteger toInt() {
        StringBuilder temp = new StringBuilder();
        for (int i = this.digits.length - 1; i >= 0; i--) {
            temp.append(digits[i]);
        }
        return new BigInteger(temp.reverse().toString());
    }

    boolean add(ArrayInteger num) {
        boolean result = false;
        if (num.getDigits().length <= this.getDigits().length) {
            result = true;
            int counter = 0;
            int numI = num.getDigits().length - 1;
            for (int i = this.getDigits().length - 1; i >= 0; i--) {
                if (numI >= 0) {
                    byte sum = (byte) (this.getDigits()[i] + num.getDigits()[numI--] + counter);
                    counter = sum >= 10 ? 1 : 0;
                    this.getDigits()[i] = sum >= 10 ? (byte) (sum - 10) : sum;
                } else {
                    if (counter == 1 && i >= 0) {
                        byte sum = (byte) (this.getDigits()[i]  + counter);
                        counter = sum >= 10 ? 1 : 0;
                        this.getDigits()[i] = sum >= 10 ? (byte) (sum - 10) : sum;
                    }
                    if (counter == 1 && i == 0) {
                        this.fillIt(this.getDigits());
                        result = false;
                    }
                }
            }
        } else {
            this.fillIt(this.getDigits());
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayInteger ai = new ArrayInteger(10); // 10
        ArrayInteger ai2 = new ArrayInteger(9); // 9
        Assert.assertEquals(10, ai.getDigits().length);
        BigInteger big = new BigInteger("123456789"); // 123456789
        BigInteger big2 = new BigInteger("987654321"); // 987654321
        ai.fromInt(big);
        ai2.fromInt(big2);
        Assert.assertEquals(ai.getDigits()[0], 0);
        Assert.assertEquals(ai.getDigits()[6], 6);
        big = ai.toInt();
        Assert.assertEquals(big.toString(), "123456789");
        Assert.assertTrue(ai.add(ai2));
        Assert.assertEquals(ai.toInt().toString(), "1111111110");
        for (int i = 0; i < 9; i++) {
            Assert.assertTrue(ai.add(ai2));
        }
        Assert.assertFalse(ai.add(ai2));
        Assert.assertEquals(ai.getDigits()[0], 0);
        Assert.assertEquals(ai.getDigits()[6], 0);
    }
}
