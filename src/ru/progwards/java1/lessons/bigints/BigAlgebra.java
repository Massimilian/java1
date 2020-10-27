package ru.progwards.java1.lessons.bigints;

import org.junit.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.is;

public class BigAlgebra {
    BigDecimal fastPow(BigDecimal num, int pow) {
        BigDecimal result;
        if (pow >= 0) {
            String bin = Integer.toBinaryString(pow);
            result = pow != 1 ? new BigDecimal("1") : num;
            if (pow > 1) {
                for (int i = 0; i < bin.length(); i++) {
                    result = bin.charAt(i) == '1' ? result.multiply(num) : result;
                    result = bin.length() - 1 != i ? result.multiply(result) : result;
                }
            }
        } else {
            result = BigDecimal.ONE.divide(this.fastPow(num, -1 * pow), 5, RoundingMode.HALF_UP);
        }
        return result;
    }

    BigInteger fibonacci(int n) {
        BigInteger result;
        if (n < 1) {
            throw new ArithmeticException("Value cannot be under 1.");
        } else {
            result = new BigInteger("1");
            if (n > 2 && n < 46) { // складывание значение типа int происходит быстрее, чем BigInteger
                int helperOne = 1;
                int helperTwo = 1;
                int semiresult = 1;
                for (int i = 2; i < n; i++) {
                    semiresult = helperOne + helperTwo;
                    helperOne = helperTwo;
                    helperTwo = semiresult;
                }
                result = new BigInteger(String.valueOf(semiresult));
            } else if (n >= 46) {
                BigInteger helperOne = new BigInteger("1134903170"); // число Фибоначчи №44
                BigInteger helperTwo = new BigInteger("1836311903"); // число Фибоначчи №45
                BigInteger semiresult = new BigInteger("1836311903");
                for (int i = 46; i < n; i++) {
                    semiresult = helperOne.add(helperTwo);
                    helperOne = new BigInteger(helperTwo.toString());
                    helperTwo = new BigInteger(semiresult.toString());
                }
                result = semiresult;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        BigAlgebra ba = new BigAlgebra();

        BigDecimal res1 = ba.fastPow(new BigDecimal("-2"), 10);
        BigDecimal res2 = ba.fastPow(BigDecimal.TEN, 9);
        BigDecimal res3 = ba.fastPow(BigDecimal.ZERO, 100);
        BigDecimal res4 = ba.fastPow(new BigDecimal("-2"), -3);
        BigDecimal res5 = ba.fastPow(new BigDecimal("100"), 1);
        BigDecimal res6 = ba.fastPow(BigDecimal.TEN, 0);
        BigDecimal res7 = ba.fastPow(new BigDecimal("1.1"), 10);
        Assert.assertEquals(res1.intValue(), 1024);
        Assert.assertEquals(res2.intValue(), 1000000000);
        Assert.assertEquals(res3.intValue(), 0);
        Assert.assertEquals(res4, new BigDecimal("-0.12500"));
        Assert.assertEquals(100, res5.intValue());
        Assert.assertEquals(1, res6.intValue());
        Assert.assertThat(res7.doubleValue(), is(2.5937424601));
        Assert.assertEquals(ba.fibonacci(100), new BigInteger("354224848179261915075"));
        Assert.assertEquals(ba.fibonacci(90), new BigInteger("2880067194370816120"));
        Assert.assertEquals(ba.fibonacci(1), BigInteger.ONE);
        Assert.assertEquals(ba.fibonacci(10), new BigInteger("55"));

    }
}
