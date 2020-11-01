package ru.progwards.java1.lessons.io1;

import org.junit.Assert;

import java.io.*;
import java.util.Scanner;

public class CharFilter {
    private String file;

    /**
     * Main method to edit file and write it
     * @param inFileName  is from what we read
     * @param outFileName is where we put
     * @param filter      is how we work
     */
    public static void filterFile(String inFileName, String outFileName, String filter) {
        CharFilter chf = new CharFilter();
        chf.filterFilePriv(inFileName, outFileName, filter);
    }

    /**
     * Method for work in not static place
     * @param inFileName  is from what we read
     * @param outFileName is where we put
     * @param filter      is how we work
     */
    private void filterFilePriv(String inFileName, String outFileName, String filter) {
        this.file = this.read(inFileName);
        this.edit(filter);
        this.write(outFileName);
    }

    /**
     * Method for read the file
     *
     * @param from is the destination from where we take the string
     */
    private String read(String from) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(from);
            try {
                Scanner scanner = new Scanner(fr);
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                    sb.append(System.lineSeparator());
                }
                sb.setLength(sb.length() - 1);
            } finally {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Method for write the file
     *
     * @param out is the destination where we put the file
     */
    private void write(String out) {
        try {
            try (FileWriter fw = new FileWriter(out)) {
                fw.write(this.file);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method for edit the string
     *
     * @param edit is for check us what kind of symbols we want to delete
     */
    private void edit(String edit) {
        this.file = this.file.replaceAll(String.format("[%s]", edit), "");
    }

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
        CharFilter chf = new CharFilter();
        chf.clean("text.txt");
        try {
            PrintStream out = new PrintStream(new FileOutputStream("text.txt"));
            System.setOut(out);
            System.out.println("Java - строго типизированный объектно-ориентированный язык программирования, разработанный компанией Sun Microsystems (в последующем приобретённой компанией Oracle). ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        filterFile("text.txt", "text.txt", " \\-,.()");
        Assert.assertTrue(chf.read("text.txt").contains("JavaстроготипизированныйобъектноориентированныйязыкпрограммированияразработанныйкомпаниейSunMicrosystemsвпоследующемприобретённойкомпаниейOracle"));
        chf.clean("text.txt");
    }
}
