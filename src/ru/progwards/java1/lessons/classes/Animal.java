package ru.progwards.java1.lessons.classes;

import ru.progwards.java1.lessons.interfaces.CompareWeight;

import java.util.Objects;

import static ru.progwards.java1.lessons.interfaces.CompareWeight.CompareResult.*;

public class Animal implements CompareWeight {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Double.compare(animal.weight, weight) == 0 &&
                ak == animal.ak;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, ak);
    }

    public String toStringFull() {
        return this.toString() + " " + calculateFoodWeight();
    }

    public int getFood1kgPrice() {
        int result = 0;
        switch (this.fk) {
            case HAY:
                result = 20;
                break;
            case CORN:
                result = 50;
                break;
            case UNKNOWN:
                result = 0;
        };
        return result;
    }

    public double getFoodPrice() {
        return calculateFoodWeight() * getFood1kgPrice();
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeight) {
        if (smthHasWeight instanceof Animal) {
            return switch (Double.compare(this.getWeight(), ((Animal) smthHasWeight).getWeight())) {
                case -1 -> LESS;
                case 0 -> EQUAL;
                default -> GREATER;
            };
        } else {
            return null;
        }
    }

}