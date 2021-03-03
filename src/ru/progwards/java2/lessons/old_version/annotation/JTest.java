package ru.progwards.java2.lessons.old_version.annotation;

import org.junit.*;
import ru.progwards.java2.lessons.old_version.annotation.calculator.Calculator2;

/**
 * Создать небольшой тестовый фреймворк
 */
public class JTest {

    private Calculator2 calculator2;
    private Class clazz;

    /**
     * В методе отмеченном аннотацией Before создать экземпляр класса Calculator
     */
    @Before
    public void prepare() {
        calculator2 = new Calculator2();
    }

    /**
     * в методе c аннотацией After записать null в экземпляр класса калькулятор.
     */
    @After
    public void clear() {
        calculator2 = null;
    }

    /**
     * Протестировать класс Calculator.
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void run() throws ClassNotFoundException {
        Assert.assertNotNull(calculator2);
    }

    /**
     * реализовать класс JTest с методом void run(String name) throws ... где name - полное имя класса с пакетом
     * @param test
     * @throws ClassNotFoundException
     */
    public void runTest(String test) throws ClassNotFoundException {
        clazz = Class.forName(test);
        this.run();
    }
}
