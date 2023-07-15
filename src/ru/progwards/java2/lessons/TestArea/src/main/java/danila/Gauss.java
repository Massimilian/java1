package danila;

import java.util.Arrays;

public class Gauss {
    public static void main(String[] args) {
        Gauss gauss = new Gauss();
        double[] result = gauss.work(new double[][]{{2.0, 6.0, 4.0, -3}, {1.0, -5.0, -1.0, 1}, {4.0, 1.0, -2.0, 2}, {20, -1, -2, -1}},
                new double[]{0.0, -3.0, 18.0, 0});
        System.out.println(Arrays.toString(result));
    }

    public double[] work(double[][] values, double sums[]) {
        double[] result = null;
        if (checkIfReal(values, sums)) {
            result = new double[sums.length];
            for (int i = 0; i < result.length; i++) {
                replacer(values, sums, i);
                findVal(values, sums, i);
            }
        }
        return finalPrepare(sums);
    }

    private double[] finalPrepare(double[] sums) {
        for (int i = 0; i < sums.length; i++) {
            sums[i] = Math.round(sums[i]*10000)/10000;
        }
        return sums;
    }

    private void replacer(double[][] values, double[] sums, int position) {
        for (int i = 0; i < values.length; i++) {
            if (values[i][position] == 1) {
                double[] temp = values[i];
                double tempSum = sums[i];
                values[i] = values[0];
                sums[i] = sums[0];
                values[0] = temp;
                sums[0] = tempSum;
                break;
            }
        }
    }

    private void findVal(double[][] values, double[] sums, int position) {
        if (values[position][position] != 1) {
            changer(values, sums, position);
        }
        prepareOthers(values, sums, position);
    }

    private void prepareOthers(double[][] values, double[] sums, int position) {
        for (int i = 0; i < sums.length; i++) {
            if (i == position) {
                continue;
            }
            double changer = values[i][position];
            for (int j = 0; j < sums.length; j++) {
                values[i][j] -= changer * values[position][j];
            }
            sums[i] -= changer * sums[position];
        }
    }

    private void changer(double[][] values, double[] sums, int position) {
        double changer = values[position][position];
        for (int i = 0; i < sums.length; i++) {
            values[position][i] = values[position][i] / changer;
        }
        sums[position] /= changer;
    }

    private boolean checkIfReal(double[][] values, double[] sums) {
        boolean real = sums.length == values.length;
        if (real) {
            for (int i = 0; i < values.length; i++) {
                real = values[i].length == sums.length;
                if (!real) {
                    break;
                }
            }
        }
        return real;
    }
}
