package ru.progwards.java2.lessons.annotation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.progwards.java2.lessons.annotation.annotations.AfterAnn;
import ru.progwards.java2.lessons.annotation.annotations.BeforeAnn;
import ru.progwards.java2.lessons.annotation.annotations.TestAnn;
import ru.progwards.java2.lessons.annotation.calculator.Calculator2;

public class JTest2 {
    private Calculator2 calc;

    @BeforeAnn
    @Before
    public void create() {
        calc = new Calculator2();
    }

    @AfterAnn
    @After
    public void delete() {
        calc = null;
    }

    @TestAnn
    @Test
    public void test() {
        Assert.assertNotNull(calc);
    }
}
