package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to save Fibo numbers
 */
public class FiboMapCache {
    private final Map<Integer, BigDecimal> fiboCache = new HashMap<>();
    private final boolean cacheOn;

    public FiboMapCache(boolean cacheOn) {
        this.cacheOn = cacheOn;
    }

    /**
     * Cache switch
     *
     * @return true(on) / false(off)
     */
    public boolean isCacheOn() {
        return cacheOn;
    }

    /**
     * Method to put a new Fibo value
     *
     * @param n is position of Fibo number
     * @return Fibo number at position n
     */
    public BigDecimal fiboNumber(int n) {
        BigDecimal result = null;
        if (this.cacheOn) {
            if (!fiboCache.containsKey(n)) {
                fiboCache.put(n, calculateFibo(n));
                result = fiboCache.get(n);
            } else {
                result = fiboCache.get(n);
            }
        } else {
            result = calculateFibo(n);
        }
        return result;
    }

    /**
     * Method for delete all values from cache
     */
    public void clearCahe() {
        fiboCache.clear();
    }

    /**
     * Method for showing test cache results with cache-on and cache-off
     */
    public static void test() {
        FiboMapCache fmct = new FiboMapCache(true);
        System.out.println(test(fmct));
        fmct.clearCahe();
        FiboMapCache fmcf = new FiboMapCache(false);
        System.out.println(test(fmcf));
        fmcf.clearCahe();
    }

    /**
     * Method to test cache
     *
     * @param fmc for test
     * @return String with result
     */
    public static String test(FiboMapCache fmc) {
        Instant inst = Instant.now();
        for (int i = 1; i < 1000; i++) {
            fmc.fiboNumber(i);
        }
        Instant instTwo = Instant.now();
        Duration duration = Duration.between(inst, instTwo);
        return String.format("fiboNumber cacheOn=%b время выполнения %d", fmc.isCacheOn(), duration.getNano() / 1000000);
    }

    /**
     * Method to calculate Fibo number from position n
     *
     * @param n position of Fibo number
     * @return Fibo number
     */
    private BigDecimal calculateFibo(int n) {
        BigDecimal result;
        if (n < 1) {
            throw new ArithmeticException("Value cannot be under 1.");
        } else {
            result = new BigDecimal("1");
            if (n > 2 && n < 46) { // складывание значение типа int происходит быстрее, чем BigInteger
                int helperOne = 1;
                int helperTwo = 1;
                int semiresult = 1;
                for (int i = 2; i < n; i++) {
                    semiresult = helperOne + helperTwo;
                    helperOne = helperTwo;
                    helperTwo = semiresult;
                }
                result = new BigDecimal(String.valueOf(semiresult));
            } else if (n >= 46) {
                BigDecimal helperOne = new BigDecimal("1134903170");
                BigDecimal helperTwo = new BigDecimal("1836311903");
                BigDecimal semiresult = new BigDecimal("1836311903");
                for (int i = 46; i < n; i++) {
                    // corrected
                    semiresult = helperOne.add(helperTwo);
                    helperOne = helperTwo;
                    helperTwo = semiresult;
                }
                result = semiresult;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        test();
        FiboMapCache fmc = new FiboMapCache(true);
        System.out.println(fmc.fiboNumber(1));
    }
}
