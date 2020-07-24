package ru.progwards.java2.lessons.tests.test.calc.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SimpleCalculatorAddTest.class,
        SimpleCalculatorDiffTest.class,
        SimpleCalculatorDivTest.class,
        SimpleCalculatorMultTest.class,
        SimpleCalculatorCombineTest.class
})

public class SimpleCalculatorFinalTest {
}
