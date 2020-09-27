package ru.progwards.java1.lessons.classes;

import org.junit.Assert;

public class AnimalTest {
    public static void main(String[] args) {
        Animal animal = new Cow(100);
        Assert.assertEquals("I am COW, eat HAY 5.0", animal.toStringFull());
        animal = new Hamster(5);
        Assert.assertEquals("I am HAMSTER, eat CORN 0.15", animal.toStringFull());
        animal = new Duck(1);
        Assert.assertEquals("I am DUCK, eat CORN", animal.toString());
    }
}
