package ru.progwards.java1.lessons.io1;

import org.junit.Assert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LineCount {
    /**
     * Method to find a count of empty strings
     * @param fileName of the file
     * @return number of empty strings
     */
    public static int calcEmpty(String fileName) {
        int count = 0;
        try {
            try (FileReader ignored = new FileReader(fileName)) {
                Scanner scanner = new Scanner(new FileReader(fileName));
                while (scanner.hasNextLine()) {
                    count = scanner.nextLine().isEmpty() ? count + 1 : count;
                }
            }
        } catch (IOException e) {
            count = -1;
        }
        return count;
    }

    /**
     * Method for adding information for check the work of the method
     * @param info - information into the file
     */
    private static void addInfo(String info) {
        try {
            try (FileWriter fw = new FileWriter("text.txt")) {
                fw.write(info);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        addInfo("this is \na string with \n3 strings and 0 empties.\n");
        Assert.assertEquals(calcEmpty("text.txt"), 0);
        addInfo("this is \na string with \n3 strings and 1 empties.\n\n");
        Assert.assertEquals(calcEmpty("text.txt"), 1);
        addInfo("this is \na string with \n3 strings and 2 empties.\n\n\n");
        Assert.assertEquals(calcEmpty("text.txt"), 2);
    }
}
