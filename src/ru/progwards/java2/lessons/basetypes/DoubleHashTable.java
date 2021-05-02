package ru.progwards.java2.lessons.basetypes;

public class DoubleHashTable<K, V> {
    Bean<V> nullable;
    Bean<V>[] array;
    int maxAttempts;
    private final int N = 7;
    private final int A = 11;

    public DoubleHashTable() {
        this.array = (Bean<V>[]) new Object[12];
        this.nullable = new Bean<>(0, null);
        this.maxAttempts = 10;
    }

//
//    public void add(K key, V value) {
//        int position = key.hashCode() % this.array.length;
//        collisAdd(position, value, 1);
//    }
//
//    private void collisAdd(int key, V value, int step) {
//        int position = key % this.array.length;
//        if (array[position] == null || array[position] == this.nullable) {
//            array[position] = new Bean<V>(key, value);
//        } else {
//            position = stepOfCollis(position);
//            if (step > this.array.length / 10) {
//                restruirAll();
//            }
//            collisAdd(position, value, ++step);
//        }
//    }
//
//    private void restruirAll() {
//        Bean<V>[] temp = (Bean<V>[]) new Object [this.simpleNumber(array.length * 2)];
//        System.arraycopy(array, 0, temp, 0, array.length);
//        array = temp;
//    }
//
//    private int stepOfCollis(int position) {
//        int s = position - position / A * 1000;
//        return Math.abs(N * (position - position / A * 1000) + 1);
//    }
//
//    private int simpleNumber(int number) {
//        boolean isSimple = false;
//        while (!isSimple) {
//            number++;
//            isSimple = true;
//            for (int i = 2; i <= (int) Math.sqrt(number); i++) {
//                if (number % i == 0) {
//                    isSimple = false;
//                    break;
//                }
//            }
//        }
//        return number;
//    }

    public static void main(String[] args) {
        DoubleHashTable<Integer, String> dht = new DoubleHashTable<>();
//        dht.add(1, "One");
//        dht.add(1, "Two");
//        dht.add(1, "Three");
//        dht.add(1, "Four");
//        dht.add(1, "Five");
    }
}
