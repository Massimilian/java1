package ru.progwards.java2.lessons.generics;

public class Main {
    public static void main(String[] args) {
//        ArraySort as = new ArraySort();
//        String[] result = as.sort("f", "e", "b", "g", "h", "k", "a", "i", "c", "d", "j", "l");
//        String[] result2 = as.sort("i", "g", "l", "d", "j", "e", "b", "f", "k", "a", "c", "h");
//        boolean check = true;
//        for (int i = 0; i < result.length; i++) {
//            check = result[i] == result2[i];
//            if (!check) {
//                break;
//            }
//        }
//        System.out.println(check);

        // ----------
        DynamicArray<String> da = new DynamicArray<>();
        System.out.println(da.getSize());
        da.insert(1, "b");
        System.out.println(da.getSize());
        da.insert(0, "a");
        System.out.println(da.getSize());
        da.add("c");
        da.insert(3, "d");
        System.out.println(da.getSize());
        da.remove(1);
        System.out.println(da.getSize());
        da.remove(1);
        System.out.println(da.getSize());
        da.remove(1);
        System.out.println(da.getSize());
        da.remove(1);
        System.out.println(da.getSize());
        da.remove(0);
        System.out.println(da.getSize());
        da.remove(0);
        System.out.println(da.getSize());
        da.remove(0);
        System.out.println(da.getSize());
        da.remove(0);
        System.out.println(da.getSize());


        System.out.println();

    }
}
