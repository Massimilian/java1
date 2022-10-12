package app;

public class SimpleCalculator implements Calculator {
    @Override
    public int mult(int x, int y) {
        return x * y;
    }

    @Override
    public int div(int x, int y) {
        int result = 0;
        if (y == 0) {
            System.out.println("Impossible operation!");
        } else {
            result = x / y;
        }
        return result;
    }

    @Override
    public int sum(int x, int y) {
        return x + y;
    }

    @Override
    public int minus(int x, int y) {
        return x - y;
    }
}
