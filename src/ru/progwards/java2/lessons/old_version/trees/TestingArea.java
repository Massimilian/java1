package ru.progwards.java2.lessons.old_version.trees;

public class TestingArea {
    public static void main(String[] args) throws TreeException {
        AvlBinaryTree<Integer, Integer> abt = new AvlBinaryTree<>();
        for (int i = 0; i < 40; i++) {
            abt.add(i, i);
        }
        for (int i = 0; i < 10; i++) {
            abt.delete(i);
        }
        System.out.println();
   }

}
