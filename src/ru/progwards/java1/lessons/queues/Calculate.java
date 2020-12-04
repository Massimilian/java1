package ru.progwards.java1.lessons.queues;

/**
 * Class for easy calculate
 */
public class Calculate {

    /**
     * static method for calculate 2.2*(3+12.1)
     *
     * @return result of calculation
     */
    public static double calculation1() {
        Calculate calculate = new Calculate();
        double result = 0d;
        try {
            result = calculate.calc1();
        } catch (StackException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * static method for calculate (737.22+24)/(55.6-12.1)+(19-3.33)*(87+2*(13.001-9.2))
     *
     * @return result of calculation
     */
    public static double calculation2() {
        Calculate calculate = new Calculate();
        double result = 0d;
        try {
            result = calculate.calc2();
        } catch (StackException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * non-static method for calculate 2.2*(3+12.1)
     *
     * @return result of calculation (33.22)
     * @throws StackException
     */
    private double calc1() throws StackException {
        StackCalc stack = new StackCalc();
        stack.push(2.2d);
        stack.push(3d);
        stack.push(12.1d);
        stack.add();
        stack.mul();
        return stack.pop();
    }

    /**
     * non-static method for calculate (737.22+24)/(55.6-12.1)+(19-3.33)*(87+2*(13.001-9.2))
     *
     * @return result of calculation (1499.9126503448276)
     * @throws StackException
     */
    private double calc2() throws StackException {
        StackCalc workPlace = new StackCalc();
        calc(19d, 3.33d, "sub", workPlace);
        calc(13.001d, 9.2d, "sub", workPlace);
        calc(workPlace.pop(), 2d, "mul", workPlace);
        calc(workPlace.pop(), 87d, "add", workPlace);
        calc(workPlace.pop(), workPlace.pop(), "mul", workPlace);
        calc(55.6d, 12.1d, "sub", workPlace);
        calc(737.22d, 24d, "add", workPlace);
        calc(workPlace.pop(), workPlace.pop(), "div", workPlace);
        calc(workPlace.pop(), workPlace.pop(), "add", workPlace);
        return workPlace.pop();
    }

    /**
     * provate method for make calculate one operation
     * @param first value
     * @param second value
     * @param action with to values
     * @param workPlace stack for care
     * @throws StackException
     */
    private void calc(double first, double second, String action, StackCalc workPlace) throws StackException {
        StackCalc draft = new StackCalc();
        draft.push(first);
        draft.push(second);
        switch (action) {
            case "add" -> draft.add();
            case "sub" -> draft.sub();
            case "mul" -> draft.mul();
            case "div" -> draft.div();
            default -> throw new StackException("Unknown operation");
        }
        workPlace.push(draft.pop());
    }

    public static void main(String[] args) {
        assert calculation1() == 33.22d;
        assert calculation2() == 1499.9126503448276d;
    }
}
