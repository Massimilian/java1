package ru.progwards.java1.lessons.interfaces;

public class CompareWeightInteger implements CompareWeight{
    private final int number;

    public int getNumber() {
        return number;
    }

    public CompareWeightInteger(int number) {
        this.number = number;
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeight) {
//        if (smthHasWeight instanceof CompareWeightInteger) {
//            return switch (Integer.compare(this.getNumber(), ((CompareWeightInteger) smthHasWeight).getNumber())) {
//                case -1 -> CompareResult.LESS;
//                case 0 -> CompareResult.EQUAL;
//                case 1 -> CompareResult.GREATER;
//                default -> null;
//            };
//        }
//        return null;
        CompareResult result = null;
        if (smthHasWeight instanceof CompareWeightInteger) {
            switch (Integer.compare(this.getNumber(), ((CompareWeightInteger) smthHasWeight).getNumber())) {
                case -1:
                    result = CompareResult.LESS;
                    break;
                case 0:
                    result = CompareResult.EQUAL;
                    break;
                case 1:
                    result = CompareResult.GREATER;
                    break;
            }
        }
        return result;
    }
}
