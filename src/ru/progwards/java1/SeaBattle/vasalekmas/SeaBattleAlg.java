package ru.progwards.java1.SeaBattle.vasalekmas;

import ru.progwards.java1.SeaBattle.SeaBattle;

import java.util.Arrays;

public class SeaBattleAlg {
    // Тестовое поле создаётся конструктором
    //     SeaBattle seaBattle = new SeaBattle(true);
    //
    // Обычное поле создаётся конструктором по умолчанию:
    //     SeaBattle seaBattle = new SeaBattle();
    //     SeaBattle seaBattle = new SeaBattle(false);
    //
    // Посмотреть результаты стрельбы можно в любой момент,
    // выведя объект класса SeaBattle на консоль. Например так:
    //     System.out.println(seaBattle);
    //
    //
    // Вид тестового поля:
    //
    //           0 1 2 3 4 5 6 7 8 9    координата x
    //         0|.|.|.|.|.|.|.|X|.|.|
    //         1|.|.|.|.|.|X|.|.|.|.|
    //         2|X|X|.|.|.|.|.|.|.|.|
    //         3|.|.|.|.|.|.|.|X|X|X|
    //         4|.|.|.|.|X|.|.|.|.|.|
    //         5|.|.|.|.|X|.|.|Х|.|.|
    //         6|.|.|.|.|.|.|.|Х|.|X|
    //         7|X|.|X|.|.|.|.|Х|.|X|
    //         8|X|.|.|.|.|.|.|X|.|.|
    //         9|X|.|.|.|X|.|.|.|.|.|

    char[][] field;
    char[][] test;
    int[] ships;
    int numberOfHits = 1;

    /**
     * Start initialisation method.
     *
     * @param seaBattle is a general field for work.
     */
    void init(SeaBattle seaBattle) {
        field = new char[seaBattle.getSizeY()][seaBattle.getSizeX()];
        for (int i = 0; i < seaBattle.getSizeY(); i++) {
            Arrays.fill(field[i], ' ');
        }
    }

    /**
     * Method for initialization the Test methods.
     *
     * @param seaBattle is a general field for work.
     */
    private void initTest(SeaBattle seaBattle) {
        this.test = new char[seaBattle.getSizeY()][seaBattle.getSizeX()];
        for (int i = 0; i < seaBattle.getSizeY(); i++) {
            Arrays.fill(this.test[i], '*');
        }
        ships = new int[]{1, 2, 3, 4};
    }

    /**
     * Method of main algorithm.
     *
     * @param seaBattle is a general field for work.
     */
    public void battleAlgorithm(SeaBattle seaBattle) {
        int count = 4;
        int roundNumber = 0;
        initTest(seaBattle);
        for (int i = 0; i < 4; i++) {
            this.round(seaBattle, count, roundNumber);
            count--;
            roundNumber++;
            if (allShipsDown()) {
                return;
            }
        }
    }

    /**
     * The method of action round
     *
     * @param seaBattle is a general field for work
     * @param count     is a size of ships for find
     * @param round     is a number of new round
     */
    private void round(SeaBattle seaBattle, int count, int round) {
        int x = 0;
        int y = 0;
        int step = count;
        while (y < 10) {
            if (test[x][y] == '*') {
                SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
                if (fireResult == SeaBattle.FireResult.HIT || fireResult == SeaBattle.FireResult.DESTROYED) {
                    test[x][y] = 'Z';
                    if (fireResult == SeaBattle.FireResult.HIT) {
                        hardWork(seaBattle, x, y);
                    } else {
                        ships[3]--;
                    }
                } else if (fireResult == SeaBattle.FireResult.MISS) {
                    test[x][y] = '0';
                }
                addNulls();
            }
            x += count;
            if (x >= 10) {
                x = count - --step;
                if (x == count) {
                    step = count;
                    x = 0;
                }
                y++;
            }
            if (allShipsDown() || ships[round] == 0) {
                break;
            }
        }
    }

    /**
     * Method for check that all ships are down
     *
     * @return all ships are down
     */
    private boolean allShipsDown() {
        boolean result = true;
        for (int ship : ships) {
            result = ship == 0;
            if (!result) {
                break;
            }
        }
        return result;
    }

    /**
     * Method of main work with concrete position
     *
     * @param seaBattle   is a general field
     * @param x-parameter
     * @param y-parameter
     */
    private void hardWork(SeaBattle seaBattle, int x, int y) {
        String result = "";
        boolean[] possibleSteps = new boolean[]{x > 0 && test[x - 1][y] == '*', x < 9 && test[x + 1][y] == '*',
                y < 9 && test[x][y + 1] == '*', y > 0 && test[x][y - 1] == '*'};
        while (!result.equals("Killed")) {
            if (possibleSteps[0]) {
                action(result = tryRightLeftStep(seaBattle, x, y, -1, false), new int[]{0, 2, 3, 1}, possibleSteps);
            }
            if (possibleSteps[1]) {
                action(result = tryRightLeftStep(seaBattle, x, y, 1, false), new int[]{1, 2, 3, 0}, possibleSteps);
            }
            if (possibleSteps[2]) {
                action(result = tryRightLeftStep(seaBattle, x, y, 1, true), new int[]{2, 0, 1, 3}, possibleSteps);
            }
            if (possibleSteps[3]) {
                action(result = tryRightLeftStep(seaBattle, x, y, -1, true), new int[]{3, 0, 1, 2}, possibleSteps);
            }
        }
    }

    /**
     * Method for corrections of the next step
     *
     * @param result        is a String with result of last shooting
     * @param ints          ism the order of destinations
     * @param possibleSteps destinations
     */
    private void action(String result, int[] ints, boolean[] possibleSteps) {
        possibleSteps[ints[0]] = possibleSteps[ints[0]] && threeTrueMakeFalse(result);
        possibleSteps[ints[1]] = possibleSteps[ints[1]] && twoTrueMakeFalse(result);
        possibleSteps[ints[2]] = possibleSteps[ints[2]] && twoTrueMakeFalse(result);
        possibleSteps[ints[3]] = possibleSteps[ints[3]] && oneTrueMakesFalse(result);
    }

    /**
     * Is possible step with one prohibition
     *
     * @param result is a String with result of last shooting
     * @return is possibility
     */
    private boolean oneTrueMakesFalse(String result) {
        return !result.equals("Killed");
    }

    /**
     * Is possible step with two prohibitions
     *
     * @param result is a String with result of last shooting
     * @return is possibility
     */
    private boolean twoTrueMakeFalse(String result) {
        return !result.equals("All") && !result.equals("Killed");
    }

    /**
     * Is possible step with three prohibitions
     *
     * @param result is a String with result of last shooting
     * @return is possibility
     */
    private boolean threeTrueMakeFalse(String result) {
        return !(result.equals("Change") || result.equals("All") || result.equals("Killed"));
    }

    /**
     * Method for make the shooting
     *
     * @param seaBattle   is a main field
     * @param x-parameter
     * @param y-parameter
     * @param correct     is a correct parameter to change position
     * @param rightLeft   is this right or left moving?
     * @return String with information about shooting
     */
    private String tryRightLeftStep(SeaBattle seaBattle, int x, int y, int correct, boolean rightLeft) {
        String result = "";
        boolean destination;
        if (rightLeft) {
            y += correct;
            destination = (correct < 0 ? y > 0 : y < 9) && test[x][y + correct] == '*';
        } else {
            x += correct;
            destination = correct < 0 ? x > 0 : x < 9 && test[x + correct][y] == '*';
        }
        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
        if (fireResult == SeaBattle.FireResult.HIT) {
            numberOfHits++;
            test[x][y] = 'Z';
            if (destination) {
                result = tryRightLeftStep(seaBattle, x, y, correct, rightLeft);
            } else {
                result = "All";
            }
        } else if (fireResult == SeaBattle.FireResult.DESTROYED) {
            numberOfHits++;
            checkFleet();
            test[x][y] = 'Z';
            result = "Killed";
        } else if (fireResult == SeaBattle.FireResult.MISS) {
            result = "Change";
            test[x][y] = '0';
        }
        return result;
    }

    /**
     * Method for check the number of alive ships
     */
    private void checkFleet() {
        if (numberOfHits == 2) {
            ships[2]--;
        } else if (numberOfHits == 3) {
            ships[1]--;
        } else if (numberOfHits == 4) {
            ships[0]--;
        }
        numberOfHits = 1;
    }

    /**
     * Method for surround 'Z' with 'O'
     */
    private void addNulls() {
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
                if (test[i][j] == 'Z') {
                    makeNulls(i, j);
                }
            }
        }
    }

    /**
     * Method for put nulls around 'Z'
     *
     * @param i is x-parameter
     * @param j is y-parameter
     */
    private void makeNulls(int i, int j) {
        if (i > 0 && j > 0 && test[i - 1][j - 1] == '*') {
            test[i - 1][j - 1] = '0';
        }
        if (j > 0 && test[i][j - 1] == '*') {
            test[i][j - 1] = '0';
        }
        if (i < 9 && j > 0 && test[i + 1][j - 1] == '*') {
            test[i + 1][j - 1] = '0';
        }
        if (i > 0 && test[i - 1][j] == '*') {
            test[i - 1][j] = '0';
        }
        if (i < 9 && test[i + 1][j] == '*') {
            test[i + 1][j] = '0';
        }
        if (i > 0 && j < 9 && test[i - 1][j + 1] == '*') {
            test[i - 1][j + 1] = '0';
        }
        if (j < 9 && test[i][j + 1] == '*') {
            test[i][j + 1] = '0';
        }
        if (i < 9 && j < 9 && test[i + 1][j + 1] == '*') {
            test[i + 1][j + 1] = '0';
        }
    }

    private int notInOneLine(int x, int y, int count) {
        if (x == 0 && y % 2 != 0 && count == 2) {
            System.out.println("corrected+");
            x = 1;
        } else if (x == 1 && y % 2 == 0 && count == 2) {
            System.out.println("corrected-");
            x = 0;
        }
        return x;
    }

    /**
     * Method for show the field with known positions
     */
    private void show() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(test[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }

    /**
     * Method for test the algorithm
     */
    static void test() {
        System.out.println("Sea battle");
        SeaBattle seaBattle = new SeaBattle();
        new SeaBattleAlg().battleAlgorithm(seaBattle);
        System.out.println(seaBattle.getResult());
    }

    static void comp() {
        SeaBattleAlg alg = new SeaBattleAlg();
        int result = 0;
        for (int i = 0; i < 1000; i++) {
            SeaBattle seaBattle = new SeaBattle(false);
            alg.battleAlgorithm(seaBattle);
            result += seaBattle.getResult();
        }
        System.out.println(result / 1000);
    }

    // функция для отладки
    public static void main(String[] args) {
        test();
    }
}

