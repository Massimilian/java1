package ru.progwards.java1.lessons;

public class CodeWars {
    public static int zeros(int n) {
        int count = 0;
        String[] res = Integer.toString(fact(n)).split("");
        for (int i = res.length - 1; i >= 0; i--) {
            if (res[i].equals("0")) {
                count++;
            } else {
                break;
            }
        }
        return res.length == 1 ? 0 : count;
    }

    public static int fact(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return n == 0 ? 0 : result;
    }

    public static void main(String[] args) {
        CodeWars cw = new CodeWars();
        System.out.println(CodeWars.zeros(12));
    }
}
