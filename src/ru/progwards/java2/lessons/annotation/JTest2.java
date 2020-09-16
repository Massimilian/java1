package ru.progwards.java2.lessons.annotation;

import org.junit.Assert;
import ru.progwards.java2.lessons.annotation.annotations.AfterAnn;
import ru.progwards.java2.lessons.annotation.annotations.BeforeAnn;
import ru.progwards.java2.lessons.annotation.annotations.TestAnn;
import ru.progwards.java2.lessons.annotation.calculator.Calculator2;

public class JTest2 {
    private Calculator2 calc;

    @BeforeAnn
    public void create() {
        calc = new Calculator2();
    }

    @AfterAnn
    public void delete() {
        calc = null;
    }

    @TestAnn
    public void test() {
        Assert.assertNotNull(calc);
    }
}
