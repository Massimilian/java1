public class Test {
    int GetMinrun(int n) {
        int r = 0;
        while (n >= 64) {
            r |= n & 1;
            n >>= 1;
        }
        return n + r; // на 200 вернёт 50; на 255 - 64; на 256 - 32.
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.GetMinrun(63));
    }
}
