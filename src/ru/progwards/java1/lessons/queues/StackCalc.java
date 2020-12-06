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
            stack.push(correct(stack.pop(), stack.pop()));
        }
    }

    /**
     * Method for correct divide different values
     * @param one is a divider
     * @param two is a divident
     * @return result of divide
     */
    private double correct(double one, double two) {
        BigDecimal on = new BigDecimal(one);
        BigDecimal tw = new BigDecimal(two);
        BigDecimal result = tw.divide(on);
        return result.doubleValue();
    }


    public static void main(String[] args) {
        StackCalc sc = new StackCalc();
        sc.push(10d);
        sc.push(-15.6d);
        sc.push(3d);
        sc.push(2d);
        sc.push(2d);
        sc.add();
        sc.sub();
        sc.div();
        sc.mul();
        System.out.println(sc.pop());
        //assert sc.pop() == 156d;
    }
}
