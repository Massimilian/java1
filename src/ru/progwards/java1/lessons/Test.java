package ru.progwards.java1.lessons;

import java.io.*;
import java.util.Scanner;

public class Test {

    private int lineCount(String filename) throws IOException {
        int result = 0;
        try {
            FileReader fr = new FileReader(filename);
            Scanner scanner = new Scanner(fr);
            while(scanner.hasNextLine()) {
                result++;
                scanner.nextLine();
            }
        } catch (Exception e) {
            throw new IOException("файл не найден");
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Test t = new Test();
        System.out.println(t.lineCount("text.txt"));
    }
}