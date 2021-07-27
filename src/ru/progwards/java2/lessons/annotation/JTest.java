package ru.progwards.java2.lessons.annotation;

import ru.progwards.java2.lessons.calculator.Calculator;
import ru.progwards.java2.lessons.calculator.CalculatorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PriorityQueue;

public class JTest {
    Object object;

    @Before(object = Calculator.class)
    private void before(String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, JTestException {
        Class clas = JTest.class;
        lookUsagesAfterAndBefore(clas);
        Class forTest;
        try {
            forTest = Class.forName(name);
        } catch (ClassNotFoundException e) {
            Method method = clas.getDeclaredMethod("before", String.class);
            Before before = method.getAnnotation(Before.class);
            forTest = before.object();
        }
        Constructor constructor = forTest.getDeclaredConstructor();
        this.object = constructor.newInstance();
    }

    private void lookUsagesAfterAndBefore(Class clas) throws JTestException {
        Method[] methods = clas.getDeclaredMethods();
        int afters = 0;
        int befores = 0;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getAnnotation(After.class) != null) {
                afters++;
            }
            if (methods[i].getAnnotation(Before.class) != null) {
                befores++;
            }
        }
        if (afters > 1) {
            throw new JTestException("Too many @After annotated methods.");
        }
        if (befores > 1) {
            throw new JTestException("Too many @Before annotated methods.");
        }
    }

    @After
    private void after() {
        object = null;
    }

    private void testsGo() throws InvocationTargetException, IllegalAccessException, JTestException {
        JTest jtest = new JTest();
        Class clas = jtest.getClass();
        Method[] methods = clas.getDeclaredMethods();
        PriorityQueue<PriorityMethod> queueOfMethods = prepareTestMethods(methods);
        while (!queueOfMethods.isEmpty()) {
            Method method = queueOfMethods.poll().getMethod();
            StringBuilder sb = new StringBuilder();
            if ((Boolean) method.invoke(jtest, this.object)) {
                sb.append("Method ");
                sb.append(method.getName());
                sb.append(" has been passed.");
                System.out.println(sb.toString());
            } else {
                sb.append("Method ");
                sb.append(method.getName());
                sb.append(" hasn't been passed.");
                throw new JTestException(sb.toString());
            }
        }
    }

    private PriorityQueue<PriorityMethod> prepareTestMethods(Method[] methods) {
        PriorityQueue<PriorityMethod> queueOfMethods = new PriorityQueue<>();
        for (int i = 0; i < methods.length; i++) {
            Test annotation = methods[i].getAnnotation(Test.class);
            if (annotation != null) {
                queueOfMethods.add(new PriorityMethod(methods[i], annotation.priority()));
                continue;
            }
        }
        return queueOfMethods;
    }


    @Deprecated
    @Test(priority = 1)
    private boolean firstPriorityTest(Object object) throws CalculatorException {
        return ((Calculator) object).calculate("2+2") == 4;
    }

    @Test(priority = 3)
    private boolean thirdPriorityTest(Object object) throws CalculatorException {
        return ((Calculator) object).calculate("(2+2)*(2+2)*(2+2)") == 64;
    }

    private void run(String name) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JTestException, ClassNotFoundException {
        before(name);
        testsGo();
        after();
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, JTestException, ClassNotFoundException {
        JTest jt = new JTest();
        jt.run("ru.progwards.java2.lessons.calculator.Calculator");
        System.out.println();
        jt.run("wrong Calculator address");
    }
}
