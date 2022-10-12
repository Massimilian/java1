package app;

import org.springframework.beans.factory.annotation.Value;

public class AdvancedCalculator extends SimpleCalculator {

    @Value("${spring.calculator.pi}")
    public String PI;

    @Override
    public int mult(int x, int y) {
        System.out.println(PI);
        long result = (long) x * (long) y;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new RuntimeException("Impossible number.");
        }
        return super.mult(x, y);
    }

    @Override
    public int div(int x, int y) {
        return super.div(x, y);
    }

    @Override
    public int sum(int x, int y) {
        long result = (long) x + (long) y;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new RuntimeException("Impossible number.");
        }
        return super.sum(x, y);
    }

    @Override
    public int minus(int x, int y) {
        long result = (long) x - (long) y;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new RuntimeException("Impossible number.");
        }
        return super.minus(x, y);
    }
}
