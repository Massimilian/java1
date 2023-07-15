package app;

public class SimpleCalculator implements Calculator{
    public SimpleCalculator() {
        System.out.println("SIMPLE CALCULATOR");
    }

    @Override
    public int sum(int x, int y) {
        return x + y;
    }

    @Override
    public int diff(int x, int y) {
        return x - y;
    }
}
