package Calculator;

public class SimpleCalculator implements ICalculator{
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int diff(int a, int b) {
        return a - b;
    }

    @Override
    public int div(int a, int b) {
        if (b != 0) {
            return a / b;
        } else {
            throw new RuntimeException("Cannot be divided by zero.");
        }
    }

    @Override
    public int mult(int a, int b) {
        return a * b;
    }
}
