package ru.progwards.java2.lessons.tests.test.calc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.tests.SimpleCalculator;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class SimpleCalculatorMultTest {
    private SimpleCalculator sc = new SimpleCalculator();
    public int val1;
    public int val2;
    public int result;

    public SimpleCalculatorMultTest(int val1, int val2, int result) {
        this.val1 = val1;
        this.val2 = val2;
        this.result = result;
    }

    @Parameterized.Parameters() // name = "Test №{index}: {0} * {1} = {2}"
    public static Iterable<Object> forTest() {
        return Arrays.asList(new Object[][]{
                {1, 3, 3},
                {200, -200, -40000},
                {0, 0, 0},
                {-1000, -9000, 9000000},
                {1, 1, 1}
        });
    }

    @Test
    public void WhenTryToSumValuesThenDoIt() {
        Assert.assertEquals(sc.mult(val1, val2), result);
    }
}

