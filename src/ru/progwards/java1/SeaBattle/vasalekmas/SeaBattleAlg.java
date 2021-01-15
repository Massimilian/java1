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

    char field[][];
    char test[][];
    int hits;

    void init(SeaBattle seaBattle) {
        field = new char[seaBattle.getSizeY()][seaBattle.getSizeX()];
        for (int i = 0; i < seaBattle.getSizeY(); i++) {
            Arrays.fill(field[i], ' ');
        }
    }

    public void battleAlgorithm(SeaBattle seaBattle) {
        // пример алгоритма:
        // стрельба по всем квадратам поля полным перебором
        int count = 3;
        initTest(seaBattle);
        for (int i = 0; i < 3; i++) {
            hits = this.round(seaBattle, count);
            count--;
            if (hits >= 20) {
                return;
            }
            System.out.println("new round" + System.lineSeparator());
        }
    }

    private void initTest(SeaBattle seaBattle) {
        this.test = new char[seaBattle.getSizeY()][seaBattle.getSizeX()];
        for (int i = 0; i < seaBattle.getSizeY(); i++) {
            Arrays.fill(this.test[i], '*');
        }
        this.hits = 0;
    }

    private int round(SeaBattle seaBattle, int count) {
        int x = 0;
        int y = 0;
        int step = count;
        while (y < 10) {
//            x = notInOneLine(x, y, count);

            if (test[x][y] == '*') {
                SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
                if (fireResult == SeaBattle.FireResult.HIT || fireResult == SeaBattle.FireResult.DESTROYED) {
                    hits++;
                    test[x][y] = 'Z';
                    if (fireResult == SeaBattle.FireResult.HIT) {
                        hardWork(seaBattle, x, y);
                    }
                } else if (fireResult == SeaBattle.FireResult.MISS) {
                    test[x][y] = '0';
                }
                addNulls();
                show();
            } else {
                System.out.println("The same: " + x + " " + y);
            }
            x += count;
            if (x >= 10) {
                x = count - --step;
                y++;
            }
            if (hits >= 20) {
                break;
            }
        }
        show();
        return hits;
    }

    //   ***
    //   *Z*
    //   ***
    private void hardWork(SeaBattle seaBattle, int x, int y) {
        String result = "";
        boolean up = x > 0 && test[x - 1][y] == '*';
        boolean down = x < 9 && test[x + 1][y] == '*';
        boolean right = y < 9 && test[x][y + 1] == '*';
        boolean left = y > 0 && test[x][y - 1] == '*';
        while (!result.equals("Killed")) {
            if (up) {
                result = tryUp(seaBattle, x, y);
                if (result.equals("Change from up") || result.equals("Up is all") || result.equals("Killed")) {
                    up = false;
                }
                if (result.equals("Up is all") || result.equals("Killed")) {
                    right = false;
                    left = false;
                }
                if (result.equals("Killed")) {
                    down = false;
                }
            }
            if (down) {
                result = tryDown(seaBattle, x, y);
                if (result.equals("Change from down") || result.equals("Down is all") || result.equals("Killed")) {
                    down = false;
                }
                if (result.equals("Down is all") || result.equals("Killed")) {
                    right = false;
                    left = false;
                }
                if (result.equals("Killed")) {
                    up = false;
                }
            }

            if (right) {
                result = tryRight(seaBattle, x, y);
                if (result.equals("Change from right") || result.equals("Right is all") || result.equals("Killed")) {
                    right = false;
                }
                if (result.equals("Right is all") || result.equals("Killed")) {
                    up = false;
                    down = false;
                }
                if (result.equals("Killed")) {
                    left = false;
                }
            }

            if (left) {
                result = tryLeft(seaBattle, x, y);
                if (result.equals("Change from left") || result.equals("Left is all") || result.equals("Killed")) {
                    left = false;
                }
                if (result.equals("Left is all") || result.equals("Killed")) {
                    up = false;
                    down = false;
                }
                if (result.equals("Killed")) {
                    right = false;
                }
            }
        }
    }

    private String tryLeft(SeaBattle seaBattle, int x, int y) {
        String result = "";
        y--;
        boolean left = y > 0 && test[x][y - 1] == '*';
        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
        if (fireResult == SeaBattle.FireResult.HIT) {
            hits++;
            test[x][y] = 'Z';
            if (left) {
                result = tryLeft(seaBattle, x, y);
            } else {
                result = "Left is all";
            }
        } else if (fireResult == SeaBattle.FireResult.DESTROYED) {
            hits++;
            test[x][y] = 'Z';
            result = "Killed";
        } else if (fireResult == SeaBattle.FireResult.MISS) {
            result = "Change from left";
            test[x][y] = '0';
        }
        return result;
    }

    private String tryRight(SeaBattle seaBattle, int x, int y) {
        String result = "";
        y++;
        boolean right = y < 9 && test[x][y + 1] == '*';
        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
        if (fireResult == SeaBattle.FireResult.HIT) {
            hits++;
            test[x][y] = 'Z';
            if (right) {
                result = tryRight(seaBattle, x, y);
            } else {
                result = "Right is all";
            }
        } else if (fireResult == SeaBattle.FireResult.DESTROYED) {
            hits++;
            test[x][y] = 'Z';
            result = "Killed";
        } else if (fireResult == SeaBattle.FireResult.MISS) {
            result = "Change from right";
            test[x][y] = '0';
        }
        return result;
    }

    private String tryDown(SeaBattle seaBattle, int x, int y) {
        String result = "";
        x++;
        boolean down = x < 9 && test[x + 1][y] == '*';
        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
        if (fireResult == SeaBattle.FireResult.HIT) {
            hits++;
            test[x][y] = 'Z';
            if (down) {
                result = tryDown(seaBattle, x, y);
            } else {
                result = "Down is all";
            }
        } else if (fireResult == SeaBattle.FireResult.DESTROYED) {
            hits++;
            test[x][y] = 'Z';
            result = "Killed";
        } else if (fireResult == SeaBattle.FireResult.MISS) {
            result = "Change from down";
            test[x][y] = '0';
        }
        return result;
    }

    private String tryUp(SeaBattle seaBattle, int x, int y) {
        String result = "";
        x--;
        boolean up = x > 0 && test[x - 1][y] == '*';
        SeaBattle.FireResult fireResult = seaBattle.fire(x, y);
        if (fireResult == SeaBattle.FireResult.HIT) {
            hits++;
            test[x][y] = 'Z';
            if (up) {
                result = tryUp(seaBattle, x, y);
            } else {
                result = "Up is all";
            }
        } else if (fireResult == SeaBattle.FireResult.DESTROYED) {
            hits++;
            test[x][y] = 'Z';
            result = "Killed";
        } else if (fireResult == SeaBattle.FireResult.MISS) {
            result = "Change from up";
            test[x][y] = '0';
        }
        return result;
    }


    private void addNulls() {
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
                if (test[i][j] == 'Z') {
                    makeNulls(i, j);
                }
            }
        }
    }

    //  ***
    //  *Z*
    //  ***
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

    private void show() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(test[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }


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
            SeaBattle seaBattle = new SeaBattle();
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

