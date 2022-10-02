package Calculator;

import java.util.Scanner;

public class UserWorker {
    ICalculator calc;

    public UserWorker(ICalculator calc) {
        this.calc = calc;
    }

    public boolean work() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your number or 'EXIT' for exit.");
        String first = scanner.nextLine().trim();
        boolean cont = false;
        if (!first.equals("EXIT")) {
            cont = true;
            System.out.println("Enter your type of operation (+, -, *, /).");
            String signal = scanner.nextLine().trim();
            System.out.println("Enter your second number");
            String second = scanner.nextLine().trim();
            int a = Integer.parseInt(first);
            int b = Integer.parseInt(second);
            int result = 0;
            switch (signal) {
                case "+":
                    result = calc.sum(a, b);
                    break;
                case "-":
                    result = calc.diff(a, b);
                    break;
                case "*":
                    result = calc.mult(a, b);
                    break;
                case "/":
                    result = calc.div(a, b);
                    break;
                default:
                    throw new RuntimeException("Incorrect command");
            }
            System.out.printf("%d %s %d = %d;%s", a, signal, b, result, System.lineSeparator());
        }
        if (!cont) {
            System.out.println("Thank you for using program!");
        }
        return cont;
    }
}
