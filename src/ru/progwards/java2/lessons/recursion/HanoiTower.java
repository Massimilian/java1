package ru.progwards.java2.lessons.recursion;

/**
 * Class for demonstrate Hanoy Tower with different sizes
 */
public class HanoiTower {
    private final int[][] tower;
    private int pos;
    private boolean on = false;

    public HanoiTower(int size, int pos) {
        this.tower = new int[size][size];
        this.fillSpike(pos);
    }

    /**
     * Method for fill all spikes with "rings"
     *
     * @param pos is a position number
     */
    private void fillSpike(int pos) {
        this.pos = pos;
        for (int i = 0; i < tower[pos - 1].length; i++) {
            tower[pos - 1][i] = i + 1;
        }
        print();
    }

    /**
     * Method to put on/off at field print
     * @param on is a interrupter
     */
    private void setTrace(boolean on) {
        this.on = on;
    }

    /**
     * Main method for move the tower
     *
     * @param from where we move
     * @param to what position we want to get
     */
    public void move(int from, int to) {
        if (from == this.pos) {
            int cycles = (tower.length - from + to) % tower.length;
            for (int i = 0; i < cycles; i++) {
                this.firstCycle();
                this.secondStep(from);
                this.finalAct(from, 2);
                this.pos = from = from++ >= tower.length ? 1 : from;
            }
        }
    }

    /**
     * Method of final constructing of the tower in recurse style
     *
     * @param from     what position the tower has been taken
     * @param ringSize the number of position
     */
    private void finalAct(int from, int ringSize) {
        if (ringSize <= tower.length) {
            for (int i = 0; i < tower.length; i++) {
                if (tower[i][0] == ringSize) {
                    changeRingPlace(i, 0, from == tower.length ? 0 : from, ringSize - 1);
                    finalAct(from, ++ringSize);
                    break;
                }
            }
        }
    }

    /**
     * Method for prepare all to a new tower
     *
     * @param from what position the tower has been taken
     */
    private void secondStep(int from) {
        int startPosition = from == tower.length - 1 ? 0 : from == tower.length ? 1 : from + 1;
        changeRingPlace(from == tower.length ? 0 : from, 0, startPosition, 1);
        changeRingPlace(from - 1, 0, from == tower.length ? 0 : from, 0);
        changeRingPlace(startPosition, 1, from - 1, 0);
    }

    /**
     * Method for destruct the tower
     */
    private void firstCycle() {
        if (isFirstCycleNotFinished()) {
            int startHeight = 0;
            for (int i = tower.length - 1; i >= 0; i--) {
                if (tower[this.pos - 1][i] != 0) {
                    startHeight = i;
                    break;
                }
            }
            int newPosition = findNewPosition();
            changeRingPlace(this.pos - 1, startHeight, newPosition, 0);
            firstCycle();
        }
    }

    /**
     * Method for move the ring
     *
     * @param startPosition start position
     * @param startHeight   height of the ring
     * @param newPosition   new position
     * @param newHeight     height of a ring on a new position
     */
    private void changeRingPlace(int startPosition, int startHeight, int newPosition, int newHeight) {
        tower[newPosition][newHeight] = tower[startPosition][startHeight];
        tower[startPosition][startHeight] = 0;
        if (on) {
            print();
        }
    }

    /**
     * Method for show the situation and pause the process
     */
    private void print() {
        for (int i = 0; i < tower.length; i++) {
            for (int[] ints : tower) {
                if (ints[i] != 0) {
                    specialPrint(ints[i]);
                } else {
                    System.out.print("  I  ");
                }
            }
            System.out.println();
        }
        System.out.println("=================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to pring the ring
     *
     * @param value is a ring number
     */
    private void specialPrint(int value) {
        System.out.print(String.format("<%s>", preparedInteger(value)));
    }

    /**
     * Method for prepare the image of a ring
     *
     * @param value is a number of ring
     * @return prepared ring (string)
     */
    private String preparedInteger(int value) {
        return value < 10 ? String.format("00%d", value) : value < 100 ? String.format("0%d", value) : String.valueOf(value);
    }

    /**
     * Method for find a new position for ring
     *
     * @return number of a new position
     */
    private int findNewPosition() {
        int result = -1;
        for (int i = pos - 1; i <= tower.length; i++) {
            if (i == tower.length) {
                i = -1;
                continue;
            }
            if (tower[i][0] == 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Method for check is this a first acr
     *
     * @return first act o no
     */
    private boolean isFirstCycleNotFinished() {
        boolean result = false;
        for (int[] ints : tower) {
            if (ints[0] == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        HanoiTower ht = new HanoiTower(10, 4);
        ht.move(4, 5);
        System.out.println();
    }
}
