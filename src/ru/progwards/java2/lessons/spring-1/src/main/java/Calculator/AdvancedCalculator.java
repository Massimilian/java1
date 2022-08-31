package Calculator;

import java.math.BigInteger;

public class AdvancedCalculator implements ICalculator {
    @Override
    public int sum(int a, int b) {
        exc(new BigInteger(String.valueOf(a)).add(new BigInteger(String.valueOf(b))));
        return a + b;
    }

    @Override
    public int diff(int a, int b) {
        exc(new BigInteger(String.valueOf(a)).subtract(new BigInteger(String.valueOf(b))));
        return a - b;
    }

    @Override
    public int div(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("Cannot be divided by zero");
        }
        return a / b;
    }

    @Override
    public int mult(int a, int b) {
        exc(new BigInteger(String.valueOf(a)).multiply(new BigInteger(String.valueOf(b))));
        return a * b;
    }

    private void exc(BigInteger bigRes) {
        if (bigRes.compareTo(new BigInteger(String.valueOf(Integer.MAX_VALUE))) > 0 || bigRes.compareTo(new BigInteger(String.valueOf(Integer.MIN_VALUE))) < 0) {
            throw new RuntimeException("Overflow");
        }
    }
}
