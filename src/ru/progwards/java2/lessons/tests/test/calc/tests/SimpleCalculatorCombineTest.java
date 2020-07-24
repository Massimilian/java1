package ru.progwards.java2.lessons.tests.test.calc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import ru.progwards.java2.lessons.tests.SimpleCalculator;

@RunWith(Enclosed.class)
public class SimpleCalculatorCombineTest {
    private static SimpleCalculator sc = new SimpleCalculator();

    public static class SimpleCalculatorExceptionsTest {

        @Test(expected = ArithmeticException.class)
        public void WhenTryToPutIncompatibleSums() {
            sc.sum(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        @Test(expected = ArithmeticException.class)
        public void WhenTryToPutIncompatibleMults() {
            sc.mult(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }

        @Test(expected = ArithmeticException.class)
        public void WhenTryToPutZeroIntoDivs() {
            sc.div(Integer.MAX_VALUE, 0);
        }

        @Test(expected = ArithmeticException.class)
        public void WhenTryToPutIncompatibleDiffs() {
            sc.diff(Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        @Test(expected = ArithmeticException.class)
        public void WhenTryToPutZeroIntoSpecDivs() {
            sc.correctDiv(Integer.MAX_VALUE, 0);
        }
    }

    public static class SimpleCalculatorSpecDivTest {
        @Test
        public void WhenTryToPutValuesIntoSpecDivThenDoItWell() {
            Assert.assertTrue(sc.correctDiv(2, 4) == 0.5);
            Assert.assertTrue(sc.correctDiv(300, 4000) == 0.075);
            Assert.assertTrue(sc.correctDiv(4, 2) == 2.0);
        }
    }
}
