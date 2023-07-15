package Renata;

import java.time.Instant;

public class AllNumbers { // 1, 2, 3
    public static void main(String[] args) {
        AllNumbers an = new AllNumbers();
        an.getNums(1, 10);
    }
    public void getNums(int num, int n) {
        System.out.println(num);
        if (num < n) {
            getNums(++num, n);
        }
    }
}

