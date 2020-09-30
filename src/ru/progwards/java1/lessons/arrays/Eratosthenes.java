package ru.progwards.java1.lessons.arrays;

import org.junit.Assert;

import java.util.Arrays;

public class Eratosthenes {
    private final boolean[] sieve;

    public Eratosthenes(int N) {
        N = Math.max(N, 0);
        sieve = new boolean[N + 1];
        Arrays.fill(sieve, true);
        this.sift();
    }

    /**
     * Filling by correct values true/false
     */
    private void sift() {
        for (int i = 2; i < sieve.length / 2; i++) {
            if (sieve[i]) {
                int j = 2;
                while (j * i < sieve.length) {
                    sieve[j++ * i] = false;
                }
            }
        }
    }

    /**
     * Checking is the number is simple. The number cannot be too big (thread  java.lang.OutOfMemoryError).
     *
     * @param n (number for check)
     * @return result of checking
     */
    public boolean isSimple(int n) {
        n = Math.abs(n);
        boolean result;
        if (n > sieve.length) { // if the value is too big
            result = new Eratosthenes(n).isSimple(n);
        } else {
            result = this.sieve[n]; // if the value is normal
        }
        return result;
    }

    public static void main(String[] args) {
        Eratosthenes eratosthenes = new Eratosthenes(-1);
        Assert.assertTrue(eratosthenes.isSimple(5));
        Assert.assertFalse(eratosthenes.isSimple(-350001));
        Assert.assertFalse(eratosthenes.isSimple(998));
    }
}
