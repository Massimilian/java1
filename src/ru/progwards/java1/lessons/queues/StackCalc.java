package ru.progwards.java1.lessons.queues;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Class of stack calculator
 */
public class StackCalc {
    private final Stack<Double> stack = new Stack<>();

    /**
     * Method for put new value
     *
     * @param value to put into stack
     */
    public void push(double value) {
        stack.push(value);
    }

    /**
     * Method for take out the top value
     *
     * @return value of the top of stack
     */
    public double pop() {
        return stack.pop();
    }

    /**
     * Method to add top values and put the result in the stack
     */
    public void add() {
        if (stack.size() > 1) {
            stack.push(stack.pop() + stack.pop());
        }
    }

    /**
     * Method to subtrack top values and put the result in the stack
     */
    public void sub() {
        if (stack.size() > 1) {
            stack.push(stack.pop() * -1 + stack.pop());
        }
    }

    /**
     * Method to multiply top values and put the result in the stack
     */
    public void mul() {
        if (stack.size() > 1) {
            stack.push(stack.pop() * stack.pop());
        }
    }

    /**
     * Method to divide top values and put the result in the stack
     */
    public void div() {
        if (stack.size() > 1) {
            stack.push(crunch(stack.pop(), stack.pop()));
        }
    }

    /**
     * Method for correct divide different values with big number of numbers
     *
     * @param one is a divider
     * @param two is a divident
     * @return result of divide
     */
    private double crunch(double one, double two) {
        String forCorrect = Double.toString(two/one);
        String[] corr = forCorrect.split("[.]");
        if (corr.length > 1 && corr[1].length() > 10) {
            corr[1] = corr[1].substring(0, 10);
        }
        forCorrect = String.format("%s.%s", corr[0], corr[1]);
        return Double.parseDouble(forCorrect);
    }


    public static void main(String[] args) {
        StackCalc sc = new StackCalc();
//        sc.push(10d);
//        sc.push(-15.6d);
//        sc.push(3d);
//        sc.push(2d);
//        sc.push(2d);
//        sc.add();
//        sc.sub();
//        sc.div();
//        sc.mul();
        sc.push(10);
        sc.push(3);
        sc.div();
        System.out.println(sc.pop());
        //assert sc.pop() == 156d;
    }
}
