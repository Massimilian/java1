package ru.progwards.java2.lessons.old_version.tests.test.calc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.old_version.tests.SimpleCalculator;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class SimpleCalculatorDiffTest {
    private SimpleCalculator sc = new SimpleCalculator();
    public int val1;
    public int val2;
    public int result;

    public SimpleCalculatorDiffTest(int val1, int val2, int result) {
        this.val1 = val1;
        this.val2 = val2;
        this.result = result;
    }

    @Parameterized.Parameters() // name = "Test №{index}: {0} - {1} = {2}"
    public static Iterable<Object> forTest() {
        return Arrays.asList(new Object[][]{
                {1, 3, -2},
                {200, -200, 400},
                {0, 0, 0},
                {-1000, -9000, 8000},
                {99999999, 87654321, 12345678}
        });
    }

    @Test
    public void WhenTryToSumValuesThenDoIt() {
        Assert.assertEquals(sc.diff(val1, val2), result);
    }

}
