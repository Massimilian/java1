package ru.progwards.java2.lessons.generics;

import java.util.ArrayList;

/**
 * Special class for work with fruit boxes
 * @param <T>
 */
public class FruitBox<T extends Fruit> extends ArrayList<T> {

    /**
     * Method for get the weight
     * @return the weight of the box
     */
    public float getWeight() {
        float result = 0;
        if (this.size() != 0) {
            result = this.get(0).getClass().getSimpleName().equals("Apple") ? this.size() : (float) (this.size() * 1.5);
        }
        return result;
    }

    /**
     * Method for move all values into the new place
     * @param fruits is an array for fill
     * @throws UnsupportedOperationException throws if the types are incompatible
     */
    public void moveTo(FruitBox fruits) throws UnsupportedOperationException {
        if (!this.isEmpty()) {
            if (fruits.isEmpty() || this.get(0).getClass().equals(fruits.get(0).getClass())) {
                fruits.addAll(this);
                this.clear();
            } else {
                throw new UnsupportedOperationException("Mixed fruits.");
            }
        }
    }

    /**
     * Method for compare two values
     * @param fruits second value for compare
     * @return -1: 1<2; 0: 1=1; 1: 1>2;
     */
    public int compareTo(FruitBox fruits) {
        return Float.compare(this.getWeight(), fruits.getWeight());
    }

    public static void main(String[] args) {
        Apple aone = new Apple();
        Apple atwo = new Apple();
        Orange oone = new Orange();
        FruitBox<Fruit> ofr = new FruitBox<>();
        ofr.add(oone);
        assert ofr.getWeight() == 1.5f;
        FruitBox<Fruit> afr = new FruitBox<>();
        afr.add(aone);
        afr.add(atwo);
        assert afr.getWeight() == 2.0f;
        assert afr.compareTo(ofr) == 1;
        assert ofr.compareTo(afr) == -1;
        assert ofr.compareTo(ofr) == 0;
        FruitBox<Fruit> nafr = new FruitBox<>();
        assert nafr.getWeight() == 0f;
        afr.moveTo(nafr);
        assert nafr.getWeight() == 2f;
        assert afr.getWeight() == 0f;
    }
}
