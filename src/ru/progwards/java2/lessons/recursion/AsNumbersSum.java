package ru.progwards.java2.lessons.recursion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AsNumbersSum {
    public static ArrayList<ArrayList<Integer>> list;

    public static String asNumbersSum(int number) {
        String result = null;
        if (number > 0) {
            list = new ArrayList<>();
            //asNumbersSum(list); // old version
            asNumberSumTwo(list, number);
            list = (ArrayList<ArrayList<Integer>>) list.stream().distinct().collect(Collectors.toList());
            result = finalPrepare(list);
        }
        return result;
    }

    private static void asNumberSumTwo(ArrayList<ArrayList<Integer>> list, int number) {
        ArrayList<Integer> last = new ArrayList<>();
        while (number != -1) {
            last.add(1);
            number--;
        }
        list.add(last);
        merge(last, 2);
    }


    private static void merge(ArrayList<Integer> temp, int num) {
        int point = 0;
        while (temp.size() - 1 != point) {
            boolean changed = false;
            if (temp.get(point) < num && point + 1 < temp.size() - 1) {
                ArrayList<Integer> newLast = (ArrayList<Integer>) temp.clone();
                temp = newLast;
                temp.set(point, temp.get(point) + 1);
                temp.set(point + 1, temp.get(point + 1) - 1);
                changed = true;
                if (temp.get(point + 1) == 0) {
                    temp.remove(point + 1);
                    point--;
                }
            }
            if (changed) {
                list.add(temp);
                merge(temp, num + 1);
            }
            point++;
        }
    }

    private static ArrayList<ArrayList<Integer>> asNumbersSum(ArrayList<ArrayList<Integer>> list) {
        ArrayList<Integer> last = list.get(list.size() - 1);
        ArrayList<Integer> newLast = (ArrayList<Integer>) last.clone();
        if (last.size() == 1 && last.get(0) != 1) {
            newLast.set(0, last.get(0) - 1);
            newLast.add(1);
            list.add(newLast);
            asNumbersSum(list);
            return list;
        }
        for (int i = last.size() - 1; i > 0; i--) {
            if (last.get(i) == 1 && last.get(i - 1) > 2) {
                newLast.set(i, last.get(i) + 1);
                newLast.set(i - 1, last.get(i - 1) - 1);
                list.add(newLast);
                asNumbersSum(list);
            }
            if (last.get(i) > 1) {
                newLast.set(i, last.get(i) - 1);
                newLast.add(1);
                list.add(newLast);
                asNumbersSum(list);
            }
        }
        if (list.get(list.size() - 1).get(0) == 2 && list.get(list.size() - 1).get(1) == 1) {
            newLast.set(0, 1);
            newLast.add(1);
            list.add(newLast);
        }
        return list;
    }

    private static String finalPrepare(ArrayList<ArrayList<Integer>> list) {
        list = (ArrayList<ArrayList<Integer>>) list.stream().sorted((o1, o2) -> comparator.compare(o1, o2)).collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Integer> temp = list.get(i);
            temp.sort((o1, o2) -> o2 - o1);
            result.append(temp.get(0));
            for (int j = 1; j < temp.size(); j++) {
                result.append(String.format("+%s", String.valueOf(temp.get(j))));
            }
            result.setLength(result.length() - 2);
            result.append(" = ");
        }
        return result.substring(0, result.length() - 3);
    }

    static Comparator<ArrayList<Integer>> comparator = new Comparator<>() {
        @Override
        public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
            int result = o1.size() - o2.size();
            int count = 0;
            while (result == 0 && count == (o1.size() > o2.size() ? o2.size() : o1.size())) {
                result = o1.get(count) - o2.get(count);
                count++;
            }
            return result;
        }
    };
}
