package Renata;

import java.util.Arrays;

public class BinarySearch<V extends Comparable<V>> {
    public static void main(String[] args) {
        BinarySearch<Integer> bs = new BinarySearch<>();
        Integer[] ints = new Integer[100000000];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }    }

    public boolean noBinarySearch(V[] arr, V v) {
        boolean isFound = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(v) == 0) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    public boolean search(V[] arr, V v) {
        Arrays.sort(arr);
        return binary(arr, v);
    }

    private boolean binary(V[] arr, V v) {
        boolean result = false;
        int x = arr.length / 2;
        if (arr.length == 1) {
            result = arr[0] == v;
        } else if (arr[x].compareTo(v) < 0) {
            V[] newArr = Arrays.copyOfRange(arr, x, arr.length);
            result = binary(newArr, v);
        } else if (arr[x].compareTo(v) > 0) {
            V[] newArr = Arrays.copyOfRange(arr, 0, x);
            result = binary(newArr, v);
        } else if (arr[x].compareTo(v) == 0) {
            result = true;
        }
        return result;
    }
}
