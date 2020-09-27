package ru.progwards.java1.lessons.classes;

public class Hamster extends Animal{
    public Hamster(double weight) {
        super(weight);
    }

    @Override
    public double getFoodCoeff() {
        return this.foodCoeff = 0.03;
    }

    @Override
    public AnimalKind getKind() {
        return ak = AnimalKind.HAMSTER;
    }

    @Override
    public FoodKind getFoodKind() {
        return fk = FoodKind.CORN;
    }
}
