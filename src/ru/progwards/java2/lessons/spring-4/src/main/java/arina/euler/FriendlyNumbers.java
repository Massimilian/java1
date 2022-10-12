package arina.euler;

public class FriendlyNumbers {
    public static int COUNT = 100;
    public static void main(String[] args) {
        FriendlyNumbers fn = new FriendlyNumbers();
        fn.tryEulers(5);
    }

    public Friends[] tryEulers(int n) {
        int count = 0;
        Friends[] result = new Friends[n];
        for (int i = 1; i < 10000; i++) {
            for (int j = i + 1; j < i + COUNT; j++) {
                Friends temp = this.findEulers(j, i);
                if (temp != null) {
                    result[count++] = temp;
                    System.out.println(temp);
                }
                if (count == n) {
                    break;
                }
            }
            if (count == n) {
                break;
            }
        }
        return result;
    }

    public Friends findEulers(long n, long m) {
        Friends fr = null;
        long p = (long) ((Math.pow(2, n - m) + 1) * Math.pow(2, m) - 1);
        long q = (long) ((Math.pow(2, n - m) + 1) * Math.pow(2, n) - 1);
        long r = (long) (Math.pow(Math.pow(2, n - m) + 1, 2) * Math.pow(2, n + m) - 1);
        if (this.isEasy(p) && this.isEasy(q) && this.isEasy(r)) {
            fr = new Friends((long) (Math.pow(2, n) * p * q), (long) (Math.pow(2, n) * r));
        }
        return fr;
    }

    private boolean isEasy(long p) {
        boolean easy = true;
        for (int i = 2; i <= p / 2; i++) {
            if (p % i == 0) {
                easy = false;
                break;
            }
        }
        return easy;
    }
}
