package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiboMapCache {
    private Map<Integer, BigInteger> fiboCache = new HashMap<>();
    private boolean cahceOn;

    public FiboMapCache(boolean cacheOn) {
        this.cahceOn = cacheOn;
    }

    public boolean isCahceOn() {
        return cahceOn;
    }

    public BigInteger fiboNumber(int n) {
        BigInteger result = null;
        if (this.cahceOn) {
            if (!fiboCache.containsKey(n)) {
                result = fiboCache.put(n, calculateFibo(n));
            } else {
                result = fiboCache.get(n);
            }
        }
        return result;
    }

    public void clearCache() {
        fiboCache.clear();
    }

    public static void test() {
        System.out.println(test(new FiboMapCache(true)));
        System.out.println(test(new FiboMapCache(false)));
    }

    public static String test(FiboMapCache fmc) {
        Instant inst = Instant.now();
        for (int i = 1; i < 1000; i++) {
            fmc.fiboNumber(i);
        }
        Instant instTwo = Instant.now();
        Duration duration = Duration.between(inst, instTwo);
        return String.format("fiboNumber cacheOn=%b время выполнения %d", fmc.isCahceOn(), duration.getNano()/1000000);
    }

    private BigInteger calculateFibo(int n) {
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
        test();
    }
}
