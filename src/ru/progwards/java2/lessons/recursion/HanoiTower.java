package ru.progwards.java2.lessons.recursion;

/**
 * Class of Hanoi tower
 */
public class HanoiTower {
    final private int SPIKE = 3;
    Ring[][] tower;
    private boolean trace = false;

    public HanoiTower(int size, int pos) {
        tower = new Ring[SPIKE][size];
        fillTower(pos);
    }

    /**
     * Method for fill all spikes by rings
     *
     * @param pos for fill
     */
    private void fillTower(int pos) {
        for (int i = tower[0].length; i > 0; i--) {
            tower[pos][tower[0].length - i] = new Ring(i);
        }
    }

    /**
     * Main method for moving rings
     *
     * @param from start position
     * @param to   finish position
     */
    public void move(int from, int to) {
        if (tower[from][tower[0].length - 1] != null) {
            move(to);
        }
    }

    /**
     * Method for recurse move with only one parameter
     *
     * @param to
     */
    private void move(int to) {
        firstStep();
        if (checkFinished(to)) {
            secondStep();
            move(to);
        } else {
            if (trace) {
                print();
            }
        }
    }

    /**
     * Method for check that is all
     *
     * @param to is a final spike
     * @return is finished
     */
    private boolean checkFinished(int to) {
        return tower[to][tower[0].length - 1] == null;
    }

    /**
     * First step of moving actions (with rings №1 and №2)
     */
    private void firstStep() {
        int fromSpike = findRing(1);
        int toSpike = getSPIKE(fromSpike, 1);
        moveRing(fromSpike, toSpike);
        moveRing(fromSpike, getSPIKE(toSpike, 1));
        moveRing(findRing(1), findRing(2));
    }

    /**
     * Method for moving one ring
     *
     * @param fromSpike from what position
     * @param toSpike   to what position
     */
    private void moveRing(int fromSpike, int toSpike) {
        if (trace) {
            print();
        }
        Ring forMove = null;
        for (int i = tower[fromSpike].length - 1; i >= 0; i--) {
            if (tower[fromSpike][i] != null) {
                forMove = tower[fromSpike][i];
                tower[fromSpike][i] = null;
                break;
            }
        }
        for (int i = tower[toSpike].length - 2; i >= 0; i--) {
            if (tower[toSpike][i] != null) {
                tower[toSpike][i + 1] = forMove;
                break;
            }
        }
        if (tower[toSpike][0] == null) {
            tower[toSpike][0] = forMove;
        }
    }

    /**
     * Method for find the ring by size
     *
     * @param size is a size of a ring
     * @return the number of spike
     */
    private int findRing(int size) {
        int result = -1;
        for (int i = 0; i < tower.length; i++) {
            for (int j = 0; j < tower[i].length; j++) {
                if (tower[i][j] != null && tower[i][j].getSize() == size) {
                    result = i;
                    break;
                }
            }
            if (result >= 0) {
                break;
            }
        }
        return result;
    }

    /**
     * Method for correct the number of spile if it is >3
     *
     * @param value
     * @param plus
     * @return
     */
    private int getSPIKE(int value, int plus) {
        return (value + plus) % SPIKE;
    }

    /**
     * Second step of movinf the ring with the size >3
     */
    private void secondStep() {
        int to = getEmptySpike();
        if (to == -1) {
            to = getBiggestRing();
        }
        int from = getThird(findRing(1), to, 0);
        moveRing(from, to);
    }

    /**
     * Method for get the third spike
     *
     * @param one    first spike
     * @param two    second spike
     * @param result maybe third spike
     * @return real third spike
     */
    private int getThird(int one, int two, int result) {
        if (result != one && result != two) {
            return result;
        } else {
            return getThird(one, two, ++result);
        }
    }

    /**
     * Method for catch the most big ring from the top
     *
     * @return number of spike
     */
    private int getBiggestRing() {
        int result = -1;
        int size = -1;
        for (int i = 0; i < tower.length; i++) {
            int res = -1;
            for (int j = 0; j < tower[i].length; j++) {
                if (tower[i][j] == null) {
                    break;
                }
                res = tower[i][j].getSize();
            }
            if (res > size) {
                result = i;
                size = res;
            }

        }
        return result;
    }

    /**
     * Method for find the empty spike
     *
     * @return the number of spike
     */
    private int getEmptySpike() {
        int result = -1;
        for (int i = 0; i < tower.length; i++) {
            if (tower[i][0] == null) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Method for initialize all actions
     */
    void print() {
        StringBuilder sb = new StringBuilder();
        for (int i = tower[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < SPIKE; j++) {
                if (tower[j][i] == null) {
                    sb.append("  I  ");
                } else {
                    sb.append(String.format("<%s>", tower[j][i].getName()));
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("=================");
        System.out.println(sb.toString());
    }

    /**
     * Switch on/off the showing of all actions
     *
     * @param on is a switcher
     */
    void setTrace(boolean on) {
        this.trace = on;
    }

    public static void main(String[] args) {
        HanoiTower ht = new HanoiTower(10, 0);
        ht.setTrace(true);
        ht.move(0, 2);
    }
}
