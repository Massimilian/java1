package ru.progwards.java2.lessons.basetypes;

public class DoubleHashTable<K, V> {
    V[] array = (V[])new Object[101];

    private int simpleNumber(int number) {
        boolean isSimple = false;
        while(!isSimple) {
            number++;
            isSimple = true;
            for (int i = 2; i <= (int) Math.sqrt(number); i++) {
                if (number % i == 0) {
                    isSimple = false;
                    break;
                }
            }
        }
        return number;
    }

    public static void main(String[] args) {
    }
}
