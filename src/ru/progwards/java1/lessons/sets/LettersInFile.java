package ru.progwards.java1.lessons.sets;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

/**
 * Class for find all letters in file.
 */
public class LettersInFile {
    private final String regex = "[A-Za-zА-Яа-яЁё]";

    /**
     * Main static method for collect all letters (Latin and Cyrillic)
     *
     * @param fileName is file with letters
     * @return collection of letters
     * @throws IOException
     */
    public static String process(String fileName) throws IOException {
        LettersInFile lif = new LettersInFile();
        return lif.proc(fileName);
    }

    /**
     * non-static method to organize all the work with file
     *
     * @param fileName is the name of file for work
     * @return String with all letters
     * @throws IOException
     */
    private String proc(String fileName) throws IOException {
        String[] array = this.readFile(fileName);
        TreeSet<String> set = this.prepareSet(array);
        String result = this.prepareString(set);
        return result;
    }

    /**
     * Method for putting Set values into the String
     *
     * @param set is organized TreeSet with letters
     * @return String with letters
     */
    private String prepareString(TreeSet<String> set) {
        return set.toString().replaceAll("[\\[\\], ]", "");
    }

    /**
     * Method for prepare TreeSet with letters (from regex)
     *
     * @param array is array to make correct values in TreeSet
     * @return
     */
    private TreeSet<String> prepareSet(String[] array) {
        TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i].matches(this.regex)) {
                set.add(array[i]);
            }
        }
        return set;
    }

    /**
     * Method for read the file
     *
     * @param fileName is a file name
     * @return String array with letters
     * @throws IOException
     */
    private String[] readFile(String fileName) throws IOException {
        StringBuilder result = new StringBuilder();
        FileReader fr = new FileReader(fileName);
        for (int ch; (ch = fr.read()) >= 0; ) {
            result.append(Character.toString(ch));
        }
        fr.close();
        return result.toString().split("");
    }

    /**
     * Special test method to prepare file in correct encoding
     *
     * @param text is a text of file
     * @throws IOException
     */
    public static void prepareForTest(String text) throws IOException {
        Path path = Paths.get("text.txt");
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.writeString(path, text);
    }

    /**
     * Special test metthod to clear all test file
     *
     * @throws IOException
     */
    public static void endTest() throws IOException {
        Path path = Paths.get("text.txt");
        Files.deleteIfExists(path);
    }

    public static void main(String[] args) throws IOException {
        prepareForTest("This is a test text. Это тестовый текст.");
        assert process("text.txt").equals("TaehistxЭвейкосты");
        endTest();
        prepareForTest("АБВГДЕЖЗИЙабвгдежзий)(*?:%;№\"12345");
        assert process("text.txt").equals("АБВГДЕЖЗИЙабвгдежзий");
        endTest();
    }
}
