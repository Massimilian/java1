package ru.progwards.java1.lessons.interfaces;

import static ru.progwards.java1.lessons.interfaces.CompareWeight.CompareResult.*;

public class Food implements CompareWeight{
    private final int weight;

    public Food(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeight) {
//        if (smthHasWeight instanceof Food) {
//            return switch (Integer.compare(this.getWeight(), ((Food) smthHasWeight).getWeight())) {
//                case -1 -> LESS;
//                case 0 -> EQUAL;
//                default -> GREATER;
//            };
//        } else {
//            return null;
//        }
        CompareResult result = null;
        if (smthHasWeight instanceof Food) {
            switch (Integer.compare(this.getWeight(), ((Food) smthHasWeight).getWeight())) {
                case -1:
                    result = LESS;
                    break;
                case 0:
                    result = EQUAL;
                    break;
                case 1:
                    result = GREATER;
                    break;
            }
        }
        return result;
    }
}
