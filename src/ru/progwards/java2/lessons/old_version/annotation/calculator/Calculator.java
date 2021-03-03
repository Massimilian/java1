package ru.progwards.java2.lessons.old_version.annotation.calculator;

// время выполнения - 2 часа 55 минут

import ru.progwards.java2.lessons.old_version.annotation.calculator.Calculator2;

/**
 * @version 0.0
 * @see Calculator2
 * Первая (неудачная) реализация калькулятора.
 * @deprecated тут есnь ошибки (в том числе синтаксические), но есть более удачная реализация
 */
public class Calculator {
    private static String[] actions = {"*", "/", "+", "-"};

    public static int calculate(String expression) {
        expression = firstPrepare(expression);
        for (int i = 0; i < 2; i++) {
            while (expression.contains(actions[i])) {
                expression = correct(expression, actions[i]);
            }
        }
        expression = sumAndMin(expression);
        if (expression.charAt(0) == 'A') {
            expression = "-" + expression.substring(1, expression.length());
        }
        return Integer.parseInt(expression);
    }

    private static String sumAndMin(String expr) {
        String[] nums = expr.split("[-+]");
        String[] symb = expr.split("[0-9]");
        int result = Integer.parseInt(nums[0]);
        int count = 0;
        for (int i = 1; i < nums.length; i++) {
            while (symb[count].equals("")) {
                count++;
            }
            if (symb[count].equals("+")) {
                result += Integer.parseInt(nums[i]);
                count++;
            } else if (symb[count].equals("-")) {
                result -= Integer.parseInt(nums[i]);
                count++;
            }
        }
        return String.valueOf(result);
    }

    private static String firstPrepare(String expr) {
        if (expr.charAt(0) == '-') {
            expr = "0" + expr;
        }
        return expr;
    }

    private static String correct(String expr, String point) {
        String peace = find(expr, point);
        expr = expr.replace(peace, calc(peace, point));
        return expr;
    }

    private static String find(String expr, String point) {
        int pos = 0;
        if (point.equals("-")) {
            pos = expr.indexOf(point);
        } else {
            pos = expr.lastIndexOf(point);
        }
        String res = expr.substring(from(expr, pos), to(expr, pos) + 1);
        return res;
    }

    private static int from(String expr, int pos) {
        int result = 0;
        for (int i = pos - 1; i >= 0; i--) {
            if (Character.toString(expr.charAt(i)).matches("[0-9, A]")) {
                result = i;
            } else {
                break;
            }
        }
        return result;
    }

    private static int to(String expr, int pos) {
        int result = 0;
        for (int i = pos + 1; i < expr.length(); i++) {
            if (Character.toString(expr.charAt(i)).matches("[0-9]")) {
                result = i;
            } else {
                break;
            }
        }
        return result;
    }

    private static String calc(String expr, String point) {
        int result = 0;
        expr = check(expr);
        String[] sum = new String[2];
        if (point.equals("*")) {
            sum = expr.split("\\" + point);
            result = Integer.parseInt(sum[0]) * Integer.parseInt(sum[1]);
        } else if (point.equals("/")) {
            sum = expr.split(point);
            result = Integer.parseInt(sum[0]) / Integer.parseInt(sum[1]);
        }
        return Integer.toString(result);
    }

    private static String check(String checker) {
        if (checker.charAt(0) == 'A') {
            checker = "-" + checker.substring(1, checker.length());
        }
        return checker;
    }
}
