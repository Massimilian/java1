package ru.progwards.java2.lessons.old_version.tests.test.calc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.old_version.tests.SimpleCalculator;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class SimpleCalculatorAddTest {

    public int val1;
    public int val2;
    public int result;
    private SimpleCalculator sc = new SimpleCalculator();

    public SimpleCalculatorAddTest(int val1, int val2, int result) {
        this.val1 = val1;
        this.val2 = val2;
        this.result = result;
    }


    @Parameterized.Parameters() // name = "Test №{index}: {0} + {1} = {2}"
    public static Iterable<Object> forTest() {
        return Arrays.asList(new Object[][]{
                {1, 3, 4},
                {200, -200, 0},
                {0, 0, 0},
                {-1000, -9000, -10000},
                {12345678, 87654321, 99999999}
        });
    }

    @Test
    public void WhenTryToSumValuesThenDoIt() {
        Assert.assertEquals(sc.sum(val1, val2), result);
    }
}
