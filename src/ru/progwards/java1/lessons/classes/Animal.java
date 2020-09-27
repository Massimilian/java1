package ru.progwards.java1.lessons.classes;

public class Animal {
    private final double weight;
    protected double foodCoeff;
    protected AnimalKind ak;
    protected FoodKind fk;

    public Animal(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getFoodCoeff() {
        return foodCoeff = 0.02;
    }

    public double calculateFoodWeight() {
        return getFoodCoeff() * getWeight();
    }

    public AnimalKind getKind() {
        return ak = AnimalKind.ANIMAL;
    }

    public FoodKind getFoodKind() {
        return fk = FoodKind.UNKNOWN;
    }

    @Override
    public String toString() {
        return "I am " + this.getKind() + ", eat " + this.getFoodKind();
    }

    public String toStringFull() {
        return this.toString() + " " + calculateFoodWeight();
    }
}
