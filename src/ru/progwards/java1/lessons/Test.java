package ru.progwards.java1.lessons;

import org.apache.logging.log4j.core.util.JsonUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

public class Test {
    static class Rectangle {
        Rectangle(BigDecimal a, BigDecimal b) {
            this.a = a;
            this.b = b;
        }
        public BigDecimal a;
        public BigDecimal b;

        boolean rectCompare(Rectangle r1, Rectangle r2) {
            return r1.a.multiply(r1.b).compareTo(r2.a.multiply(r2.b)) == 0;
        }
    }

    public static void main(String[] args) {
        BigInteger big = new BigInteger("5");
        BigInteger other = BigInteger.valueOf(10);

    }
}