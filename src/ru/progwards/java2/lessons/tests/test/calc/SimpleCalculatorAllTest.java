package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SimpleCalculatorDiffTest.class,
        SimpleCalculatorDivTest.class,
        SimpleCalculatorMultTest.class,
        SimpleCalculatorSumTest.class
})

public class SimpleCalculatorAllTest {
}
