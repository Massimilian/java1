package ru.progwards.java2.lessons.old_version.annotation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.progwards.java2.lessons.annotation.annotations.AfterAnn;
import ru.progwards.java2.lessons.annotation.annotations.TestAnn;
import ru.progwards.java2.lessons.old_version.annotation.annotations.BeforeAnn;
import ru.progwards.java2.lessons.old_version.annotation.calculator.Calculator2;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JTest2 {

    private static Calculator2 calculator2;

    @BeforeAnn
    @Before
    public void create() {
        calculator2 = new Calculator2();
    }

    @AfterAnn
    @After
    public void delete() {
        calculator2 = null;
    }

    @TestAnn
    @Test
    public void test(){
        Assert.assertNotNull(calculator2);
    }

    public static void method(Class clazz) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Constructor constructor = clazz.getDeclaredConstructor();
        Object jTest2 = constructor.newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        actionMethods(jTest2, methods, BeforeAnn.class);
        actionMethods(jTest2, methods, TestAnn.class);
        actionMethods(jTest2, methods, AfterAnn.class);
    }

    private static void actionMethods(Object object, Method[] methods, Class clazz) throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(clazz)) {
                System.out.println(String.format("%s method works.", methods[i].getName()));
                methods[i].invoke(object);
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        JTest2.method(JTest2.class);
    }

}
