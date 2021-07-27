package ru.progwards.java2.lessons.calculator;

import java.util.ArrayList;

public class Calculator {

    /**
     * Main method for calculate value
     *
     * @param expression String expression
     * @return result of calculating
     */
    public static int calculate(String expression) throws CalculatorException {
        Calculator calc = new Calculator();
        return calc.calc(expression);
    }

    /**
     * method for prepare the expression and calculate it (in not-static method)
     *
     * @param expression for calculate
     * @return result of calculating
     */
    private int calc(String expression) throws CalculatorException {
        expression = findAndRecheck(expression);
        ArrayList<String> expr = prepareString(expression);
        step(expr, "*", "/");
        step(expr, "+", "-");
        return Integer.parseInt(expr.get(0));
    }

    /**
     * Method for calculate expressions in brackets
     *
     * @param expression for find smaller expressions
     * @return snmaller expression or result
     */
    private String findAndRecheck(String expression) throws CalculatorException {
        if (expression.contains("(")) {
            int start = expression.indexOf("(");
            int end = findFinishBracket(start, expression);
            String small = expression.substring(start + 1, end);
            expression = expression.replace(stringWithBrackets(small), String.valueOf(calc(small)));
        }
        return edited(expression);
    }

    /**
     * Method for find finish position of closed bracked
     *
     * @param start      position
     * @param expression for find
     * @return position of closing bracket
     */
    private int findFinishBracket(int start, String expression) throws CalculatorException {
        int val = 0;
        int finish = -1;
        for (int i = start + 1; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                val++;
            }
            if (expression.charAt(i) == ')') {
                if (val > 0) {
                    val--;
                } else {
                    finish = i;
                }
            }
        }
        if (finish == -1) {
            throw new CalculatorException();
        }
        return finish;
    }

    /**
     * Method for replace brackets
     *
     * @param expression with brackets
     * @return expresion without brackets
     */
    private String edited(String expression) {
        expression = expression.replaceAll("\\(", "");
        expression = expression.replaceAll("\\)", "");
        return expression;
    }

    /**
     * Method for construir String with brackets
     *
     * @param small String for build
     * @return builded expression
     */
    private String stringWithBrackets(String small) {
        return "(" + small + ")";
    }

    /**
     * Method for calculate three-position value
     *
     * @param expr    - expression
     * @param symbOne first number
     * @param symbTwo second number
     */
    private void step(ArrayList<String> expr, String symbOne, String symbTwo) throws CalculatorException {
        for (int i = 1; i < expr.size(); i++) {
            if (expr.get(i).equals(symbOne) || expr.get(i).equals(symbTwo)) {
                doStep(expr, i);
                i--;
            }
        }
    }

    /**
     * Method for put calculated value into the ArrayList
     *
     * @param exp is ArrayList for edit
     * @param pos is position for edit
     */
    private void doStep(ArrayList<String> exp, int pos) throws CalculatorException {
        int result = doUnion(exp.get(pos - 1), exp.get(pos), exp.get(pos + 1));
        exp.set(pos - 1, String.valueOf(result));
        exp.remove(pos);
        exp.remove(pos);
    }

    /**
     * Method for union two numbers
     *
     * @param first  number
     * @param symbol what we need to do with two numbers
     * @param second number
     * @return result of two numbers
     */
    private int doUnion(String first, String symbol, String second) throws CalculatorException {
        return switch (symbol) {
            case "*" -> Integer.parseInt(first) * Integer.parseInt(second);
            case "/" -> Integer.parseInt(first) / Integer.parseInt(second);
            case "-" -> Integer.parseInt(first) - Integer.parseInt(second);
            case "+" -> Integer.parseInt(first) + Integer.parseInt(second);
            default -> throw new CalculatorException();
        };
    }

    /**
     * Method for prepare string and put numbers and symbols into the ArrayList
     *
     * @param expression is a sentence for divide into different values
     * @return broken expression (prepared ArrayList)
     */
    private ArrayList<String> prepareString(String expression) {
        ArrayList<String> broken = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char first = '`';
            if (expression.charAt(i) == '-' && (i == 0 || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-'
                    || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/')) {
                first = expression.charAt(i++);
            }
            StringBuilder second = new StringBuilder();
            boolean changed = false;
            while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                second.append(expression.charAt(i++));
                changed = true;
            }
            i = changed ? i - 1 : i;
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                second = new StringBuilder(String.valueOf(expression.charAt(i)));
            }
            if (first != '`') {
                broken.add(first + second.toString());
            } else {
                broken.add(second.toString());
            }
        }
        return broken;
    }

    public static void main(String[] args) throws CalculatorException {
        assert Calculator.calculate("2+2") == 4;
        assert Calculator.calculate("2+2-2") == 2;
        assert Calculator.calculate("-30--30--30") == 30;
        assert Calculator.calculate("2+-2") == 0;
        assert Calculator.calculate("2+2-2*2/2") == 2;
        assert Calculator.calculate("2*2-2/2") == 3;
        assert Calculator.calculate("2*(2-2)/2") == 0;
        assert Calculator.calculate("(2+3)*6/-30") == -1;
        assert Calculator.calculate("(((3+3)*3+3)*3+3)*3") == 198;
        assert Calculator.calculate("(2+2)*(2+2)*(2+2)") == 64;
    }
}
