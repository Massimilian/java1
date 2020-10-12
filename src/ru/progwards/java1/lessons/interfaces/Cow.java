package ru.progwards.java1.lessons.interfaces;

public class Cow extends Animal {
    public Cow(double weight) {
        super(weight);
    }

    @Override
    public double getFoodCoeff() {
        return this.foodCoeff = 0.05;
    }

    @Override
    public AnimalKind getKind() {
        return this.ak = AnimalKind.COW;
    }

    @Override
    public FoodKind getFoodKind() {
        return this.fk = FoodKind.HAY;
    }
}
