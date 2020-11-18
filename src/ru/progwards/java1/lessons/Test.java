package ru.progwards.java1.lessons;

import java.io.IOException;
import java.io.RandomAccessFile;


public class Test {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("text.txt", "rw");
        System.out.println(raf.readByte());
    }
}
