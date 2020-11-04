package ru.progwards.java1.lessons.io1;

import org.junit.Assert;

import java.io.*;
import java.util.Scanner;

public class Coder {
    /**
     * Main method to code the file
     *
     * @param inFileName  is file for code
     * @param outFileName is file to put results
     * @param code        is a way to code
     * @param logName     is an exception log
     */
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        Coder coder = new Coder();
        coder.codeFilePriv(inFileName, outFileName, code, logName);
    }

    /**
     * Method for work in a not static place (read-recode-write)
     *
     * @param inFileName  is file for code
     * @param outFileName is file to put results
     * @param code        is a way to code
     * @param logName     is an exception log
     */
    private void codeFilePriv(String inFileName, String outFileName, char[] code, String logName) {
        this.write(outFileName, this.recode(this.readToChars(inFileName, logName), code), logName);
    }

    /**
     * Reading the file
     *
     * @param file is an address of file
     * @param log  is an address of log
     * @return char[] value
     */
    private char[] readToChars(String file, String log) {
        return this.read(file, log).toCharArray();
    }


    /**
     * Method for recode the byte[]
     *
     * @param chars for recode
     * @param code  is a way to recode
     * @return recoded byte[]
     */
    private char[] recode(char[] chars, char[] code) {
        char[] result = new char[chars.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = code[(int) chars[i]];
        }
        return result;
    }

    /**
     * writing the file
     *
     * @param file  is when to write
     * @param chars is what to write
     * @param log   is what to write in a exception log
     */
    private void write(String file, char[] chars, String log) {
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            try {
                fw.write(String.valueOf(chars));
            } finally {
                fw.close();
            }
        } catch (IOException e) {
            this.write(e.getMessage(), log);
        }
    }

    /**
     * Method for fill an exception log
     *
     * @param file is where to write
     * @param info is what to write
     */
    private void write(String file, String info) {
        try {
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(info);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method for easy read
     *
     * @param file from where to read
     * @return String with information
     */
    private String read(String file, String log) {
        StringBuilder result = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            Scanner scanner = new Scanner(fr);
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine()).append(System.lineSeparator());
            }
            fr.close();
        } catch (IOException e) {
            this.write(e.getMessage(), log);
        }
        return result.toString();
    }

    /**
     * Method to keep the file place clean
     *
     * @param file is where to clean
     */
    private void clean(String file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Coder coder = new Coder();

        char[] code = new char[Character.MAX_VALUE];
        for (int i = 0; i < code.length - 1; i++) {
            code[i] = (char) (i + 1);
        }

        char[] recode = new char[Character.MAX_VALUE];
        for (int i = 1; i < recode.length; i++) {
            recode[i] = (char) (i - 1);
        }

        coder.clean("text.txt");
        coder.write("text.txt", "this is a test text");
        codeFile("text.txt", "text.txt", code, "logName.txt");
        Assert.assertTrue(coder.read("text.txt", "logName.txt").contains("uijt!jt!b!uftu!ufyu"));
        codeFile("text.txt", "text.txt", recode, "logName.txt");
        Assert.assertTrue(coder.read("text.txt", "logName.txt").contains("this is a test text"));
        coder.clean("text.txt");

    }
}