package ru.progwards.java2.lessons.annotation.calculator;

// время выполнения - 40 минут

import ru.progwards.java2.lessons.calculator.Calculator;

/**
 * @version 1.0
 * @see Calculator
 * Специальный класс, который принимает строку в качестве арифметического примера и решает его.
 * Работа возможна только с целыми числами. Возможные операции - умножение, деление, сложение и вычитание. Использование скобок допускается.
 */
public class Calculator2 {
    /**
     * Основной рабочий метод. Принимает строку (expression), возвращает результат вычисления
     *
     * @param expression
     * @return результат вычисления
     */
    public static int calculate(String expression) {
        while (expression.contains("(")) {
            expression = beforePrepare(expression);
        }
        expression = firstPrepare(expression);
        return Integer.parseInt(plusAndMinus(expression));
    }

    /**
     * проверка на наличие первого отрицательного числа и редактирование строчки (в начале прибавляется ноль)
     *
     * @param expr
     * @return подготовленная строка
     */
    private static String firstPrepare(String expr) {
        if (expr.charAt(0) == '-') {
            expr = "0" + expr;
        }
        return expr;
    }

    /**
     * Решение тех математических вычислений, которые находятся внутри скобок ("раскрытие скобок")
     *
     * @param expr
     * @return строка без скобок
     */
    private static String beforePrepare(String expr) {
        int from = 0;
        int to = 0;
        for (int i = 0; i < expr.length(); i++) {
            if (expr.charAt(i) == '(') {
                from = i;
            }
            if (expr.charAt(i) == ')') {
                to = i;
                break;
            }
        }
        String conc = expr.substring(from, to + 1);
        conc = String.valueOf(calculate(conc.substring(1, conc.length() - 1)));
        expr = expr.substring(0, from) + conc + expr.substring(to + 1, expr.length());
        return expr;
    }

    /**
     * вычисление подготовленной (без скобок) строки. Сначала - вычисление умножений и делений, затем сложений и вычитаний
     *
     * @param expr
     * @return результат математического вычисления
     */
    private static String plusAndMinus(String expr) {
        String[] nums = expr.split("[-+]");
        String[] symb = expr.split("[0-9*/]");
        int result = Integer.parseInt(nums[0]);
        int count = 0;
        nums = multiAndDiv(nums);
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

    /**
     * Выполнение умножения и деления.
     *
     * @param args
     * @return сокращённая строка
     */
    private static String[] multiAndDiv(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("*")) {
                args[i] = result(args[i], true);
            }
            if (args[i].contains("/")) {
                args[i] = result(args[i], false);
            }
        }
        return args;
    }

    /**
     * Подготовка результатов деления (если multi = false) и умножения (если multi = true)
     *
     * @param value
     * @param multi
     * @return результат вычисления
     */
    private static String result(String value, boolean multi) {
        String result;
        if (multi) {
            String[] values = value.split("\\*");
            result = String.valueOf(Integer.parseInt(values[0]) * Integer.parseInt(values[1]));
        } else {
            String[] values = value.split("/");
            result = String.valueOf(Integer.parseInt(values[0]) / Integer.parseInt(values[1]));
        }
        return result;
    }
}