package ru.progwards.java2.lessons.builders;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class SimpleCalculatorDivTest {
    private SimpleCalculator sc = new SimpleCalculator();
    public int val1;
    public int val2;
    public int result;

    public SimpleCalculatorDivTest(int val1, int val2, int result) {
        this.val1 = val1;
        this.val2 = val2;
        this.result = result;
    }

    @Parameterized.Parameters() // name = "Test №{index}: {0} / {1} = {2}"
    public static Iterable<Object> forTest() {
        return Arrays.asList(new Object[][]{
                {6, 3, 2},
                {200, -200, -1},
                {0, Integer.MIN_VALUE, 0},
                {-9000, -1000, 9},
                {99999999, 3, 33333333}
        });
    }

    @Test
    public void WhenTryToSumValuesThenDoIt() {
        Assert.assertEquals(sc.div(val1, val2), result);
    }

}
