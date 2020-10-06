package ru.progwards.java1.lessons.interfaces;

import org.junit.Assert;
import ru.progwards.java1.lessons.classes.Animal;
import ru.progwards.java1.lessons.classes.Cow;
import ru.progwards.java1.lessons.classes.Duck;
import ru.progwards.java1.lessons.classes.Hamster;

public class Test {
    public static void main(String[] args) {
        Animal first;
        Animal second;
        Food food = new Food(100);
        first = new Cow(100);
        second = new Duck(6);
        System.out.println(first.getFoodPrice()); // не получается отловить здесь ошибку. При работе через Debug всё работает, но при запуске программы через Run даёт ошибку java.lang.NullPointerException
        Assert.assertEquals(CompareWeight.CompareResult.GREATER, first.compareWeight(second));
        Assert.assertEquals(CompareWeight.CompareResult.LESS, second.compareWeight(first));
        second = new Hamster(100);
        Assert.assertEquals(CompareWeight.CompareResult.EQUAL, first.compareWeight(second));
        Assert.assertNull(first.compareWeight(food));
        Assert.assertNull(food.compareWeight(first));
        CompareWeightInteger[] a = {new CompareWeightInteger(1), new CompareWeightInteger(3), new CompareWeightInteger(2), new CompareWeightInteger(0)};
        ArraySort.sort(a);
        for (int i = 0; i < a.length; i++) {
            Assert.assertEquals(i, a[i].getNumber());
        }
        Assert.assertEquals(55, CalculateFibonacci.fiboNumber(10));
        Assert.assertEquals(10, CalculateFibonacci.getLastFibo().n);
        Assert.assertEquals(55, CalculateFibonacci.getLastFibo().fibo);
        CalculateFibonacci.clearLastFibo();
        Assert.assertNull(CalculateFibonacci.getLastFibo());
    }
}
