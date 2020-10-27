package ru.progwards.java1.lessons.bigints;

import org.junit.Assert;

public class AbsInteger {
    private final Number value;

    public AbsInteger(Number value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Number getValue() {
        return value;
    }

    static AbsInteger add(AbsInteger num1, AbsInteger num2) {
        AbsInteger result;
        long value = num1.getValue().longValue() + num2.getValue().longValue();
        if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
            result = new AbsInteger(value);
        } else if (value > Short.MAX_VALUE || value < Short.MIN_VALUE) {
            result = new IntInteger((int)value);
        } else if (value > Byte.MAX_VALUE || value < Byte.MIN_VALUE) {
            result = new ShortInteger((short)value);
        } else {
            result = new ByteInteger((byte)value);
        }
        return result;
    }

    public static void main(String[] args) {
        ByteInteger bi = new ByteInteger((byte) 5);
        ByteInteger bi2 = new ByteInteger(Byte.MAX_VALUE);
        ShortInteger shMax = new ShortInteger(Short.MAX_VALUE);
        IntInteger inMax = new IntInteger(Integer.MAX_VALUE);
        Assert.assertEquals(AbsInteger.add(bi, bi).getClass().toString(), bi.getClass().toString());
        Assert.assertEquals(15, (byte) AbsInteger.add(bi, AbsInteger.add(bi, bi)).getValue());
        Assert.assertEquals(AbsInteger.add(bi, bi2).getClass().toString(), shMax.getClass().toString());
        Assert.assertEquals(132, (short) AbsInteger.add(bi, bi2).getValue());
        Assert.assertEquals(AbsInteger.add(bi, shMax).getClass().toString(), inMax.getClass().toString());
        Assert.assertEquals(2147483652L, (long) AbsInteger.add(bi, inMax).getValue());
    }
}
