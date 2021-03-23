package ru.progwards.java2.lessons.recursion;


public class HanoiTower {
    final private int SPIKE = 3;
    Ring[][] tower;
    private boolean trace = false;

    public HanoiTower(int size, int pos) {
        tower = new Ring[SPIKE][size];
        fillTower(pos);
    }

    private void fillTower(int pos) {
        for (int i = tower[0].length; i > 0; i--) {
            tower[pos][tower[0].length - i] = new Ring(i);
        }
    }

    public void move(int from, int to) {
        if (tower[from][tower[0].length - 1] != null) {
            move(to);
        }
    }

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

    private boolean checkFinished(int to) {
        return tower[to][tower[0].length - 1] == null;
    }

    private void firstStep() {
        int fromSpike = findRing(1);
        int toSpike = getSPIKE(fromSpike, 1);
        moveRing(fromSpike, toSpike);
        moveRing(fromSpike, getSPIKE(toSpike, 1));
        moveRing(findRing(1), findRing(2));
    }

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

    private int getSPIKE(int value, int plus) {
        return (value + plus) % SPIKE;
    }


    private void secondStep() {
        int to = getEmptySpike();
        if (to == -1) {
            to = getBiggestRing();
        }
        int from = getThird(findRing(1), to, 0);
        moveRing(from, to);
    }

    private int getThird(int one, int two, int result) {
        if (result != one && result != two) {
            return result;
        } else {
            return getThird(one, two, ++result);
        }
    }

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

    void print() {
        StringBuilder sb = new StringBuilder();
        for (int i = tower[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < SPIKE; j++) {
                if (tower[j][i] == null) {
                    sb.append("  I  ");
                } else {
                    sb.append(String.format("<%s>", prepareNumber(tower[j][i].getSize())));
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("=================");
        System.out.println(sb.toString());
    }

    private String prepareNumber(int size) {
        return size < 10? "00" + size : size < 100? "0" + size : String.valueOf(size);
    }

    void setTrace(boolean on) {
        this.trace = on;
    }

    public static void main(String[] args) {
        HanoiTower ht = new HanoiTower(10, 0);
        ht.setTrace(true);
        ht.move(0, 2);
    }
}
