package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to turn any number in all possible summing strings
 */
public class AsNumbersSum {
    private final ArrayList<ArrayList<Integer>> list = new ArrayList<>();

    /**
     * Main method to prepare the summing String
     * @param number for prepare
     * @return prepares String
     */
    public static String asNumbersSum(int number) {
        AsNumbersSum ans = new AsNumbersSum();
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(number);
        ans.fastAdd(temp);
        ans.rebuild(number, new ArrayList<>());
        return ans.fin(ans.list.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * Method for add new Summing variation
     * @param value list with numbers for sum
     */
    private void fastAdd(ArrayList<Integer> value) {
        Collections.sort(value);
        Collections.reverse(value);
        this.list.add(value);
    }

    /**
     * Method for break the summing String in different variations in recurse way
     * @param number for break
     * @param temp list for new break
     */
    private void rebuild(int number, ArrayList<Integer> temp) {
        int first = number - 1;
        int second = 1;
        while (number - second != 0) {
            ArrayList<Integer> generalTemp = (ArrayList<Integer>) temp.clone();
            generalTemp.add(first--);
            ArrayList<Integer> newTemp = (ArrayList<Integer>) generalTemp.clone();
            rebuild(second, newTemp);
            generalTemp.add(second++);
            this.fastAdd(generalTemp);
        }
    }

    /**
     * Method for final prepare of String
     * @param list new variation
     * @return prepared String
     */
    public String fin(List<ArrayList<Integer>> list) {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Integer> integers : list) {
            for (Integer integer : integers) {
                sb.append(String.format("%s + ", integer));
            }
            sb.setLength(sb.length() - 3);
            sb.append(" = ");
        }
        sb.setLength(sb.length() - 3);
        return sb.toString();
    }

    public static void main(String[] args) {
        asNumbersSum(5);
        assert asNumbersSum(5).equals("5 = 4 + 1 = 3 + 1 + 1 = 3 + 2 = 2 + 2 + 1 = 2 + 1 + 1 + 1 = 1 + 1 + 1 + 1 + 1");
    }
}
