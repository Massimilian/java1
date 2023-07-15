package app;

public class AdvancedCalculator implements Calculator{

    public AdvancedCalculator() {
        System.out.println("ADVANCED CALCULATOR");
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
