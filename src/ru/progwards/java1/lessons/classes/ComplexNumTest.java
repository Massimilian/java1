package ru.progwards.java1.lessons.classes;

import org.junit.Assert;

public class ComplexNumTest {
    public static void main(String[] args) {
        ComplexNum num = new ComplexNum(1, 2);
        Assert.assertEquals("1+2i", num.toString());
        ComplexNum comp = new ComplexNum(3, 4);
        Assert.assertEquals("4+6i", comp.add(num).toString());
        Assert.assertEquals("2+2i", comp.sub(num).toString());
        Assert.assertEquals("-5+10i", comp.mul(num).toString());
        Assert.assertEquals("1+0i", new ComplexNum(6, 4).div(comp).toString());
        Assert.assertNull(new ComplexNum(0, 0).div(new ComplexNum(0, 0)));
    }
}
