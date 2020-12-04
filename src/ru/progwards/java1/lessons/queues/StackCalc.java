package ru.progwards.java1.lessons.queues;

import java.util.Stack;

/**
 * Class of stack calculator
 */
public class StackCalc {
    private final Stack<Double> stack = new Stack<>();

    /**
     * Method for put new value
     *
     * @param value
     */
    public void push(double value) {
        stack.push(value);
    }

    /**
     * Method for take out the top value
     *
     * @return value
     */
    public double pop() {
        return stack.pop();
    }

    /**
     * Method to add top values and put the result in the stack
     *
     * @throws StackException
     */
    public void add() throws StackException {
        if (stack.size() < 2) {
            throw new StackException("Exception in add method.");
        } else {
            stack.push(stack.pop() + stack.pop());
        }
    }

    /**
     * Method to subtrack top values and put the result in the stack
     *
     * @throws StackException
     */
    public void sub() throws StackException {
        if (stack.size() < 2) {
            throw new StackException("Exception in sub method.");
        } else {
            stack.push(stack.pop() * -1 + stack.pop());
        }
    }

    /**
     * Method to multiply top values and put the result in the stack
     *
     * @throws StackException
     */
    public void mul() throws StackException {
        if (stack.size() < 2) {
            throw new StackException("Exception in mul method.");
        } else {
            stack.push(stack.pop() * stack.pop());
        }
    }

    /**
     * Method to divide top values and put the result in the stack
     *
     * @throws StackException
     */
    public void div() throws StackException {
        if (stack.size() < 2) {
            throw new StackException("Exception in div method.");
        } else {
            stack.push(1 / stack.pop() * stack.pop());
        }
    }


    public static void main(String[] args) throws StackException {
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
        assert sc.pop() == 156d;
    }
}
