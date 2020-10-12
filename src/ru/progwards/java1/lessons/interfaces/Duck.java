package ru.progwards.java1.lessons.interfaces;


public class Duck extends Animal {
    public Duck(double weight) {
        super(weight);
    }

    @Override
    public double getFoodCoeff() {
        return this.foodCoeff = 0.04;
    }

    @Override
    public AnimalKind getKind() {
        return ak = AnimalKind.DUCK;
    }

    @Override
    public FoodKind getFoodKind() {
        return fk = FoodKind.CORN;
    }
}
