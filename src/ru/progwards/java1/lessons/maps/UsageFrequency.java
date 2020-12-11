package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for prepare information about words and letters
 */
public class UsageFrequency {
    private String text;

    /**
     * Method for put text from file into String text
     * @param fileName if file with
     */
    public void processFile(String fileName) {
        FileReader fr;
        try {
            fr = new FileReader(fileName);
            Scanner scanner = new Scanner(fr);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            this.text = sb.toString();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for get letters and its count
     * @return Map<letter, count>
     */
    public Map<Character, Integer> getLetters() {
        Map<Character, Integer> letters = new HashMap<>();
        for (int i = 0; i < this.text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
                if (letters.containsKey(ch)) {
                    letters.replace(ch, letters.get(ch) + 1);
                } else {
                    letters.put(ch, 1);
                }
            }
        }
        return letters;
    }

    /**
     * Method for get words and its count
     * @return Map<word, count>
     */
    public Map<String, Integer> getWords() {
        Map<String, Integer> words = new HashMap<>();
        String[] arrWord = text.split("[^A-Za-zА-Яа-я0-9]+");
        for (String s : arrWord) {
            if (!s.equals("")) {
                if (words.containsKey(s)) {
                    words.replace(s, words.get(s) + 1);
                } else {
                    words.put(s, 1);
                }
            }
        }
        return words;
    }

    /**
     * Method for put info in the file
     * @param file is for text
     * @param text is for file
     */
    private void putSomeTestInfo(String file, String text) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(text);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        UsageFrequency uf = new UsageFrequency();
        UsageFrequency wikiTrainUf = new UsageFrequency();
        UsageFrequency wikiTestUf = new UsageFrequency();
        String test = "test.txt";
        Path path = Paths.get(test);
        String wikiTrainTest = "src/ru/progwards/java1/lessons/maps/wiki.train.tokens";
        String wikiTestTest = "src/ru/progwards/java1/lessons/maps/wiki.test.tokens";
        Files.deleteIfExists(path);
        Files.createFile(path);
        uf.putSomeTestInfo(test, "abC Abc defghi jklmn jklmno o pqrstuvwxyz o O; \nпроверка*?:русских;№букв+=-букв");
        uf.processFile(test);
        wikiTrainUf.processFile(wikiTrainTest);
        wikiTestUf.processFile(wikiTestTest);
        Map<Character, Integer> map = uf.getLetters();
        Map<String, Integer> words = uf.getWords();
        Map<Character, Integer> wikiMap = wikiTrainUf.getLetters();
        Map<String, Integer> wikiWords = wikiTestUf.getWords();
        assert map.get('a') == 1;
        assert map.get('b') == 2;
        assert map.get('j') == 2;
        assert map.get('у') == 3;
        assert words.get("Abc") == 1;
        assert words.get("jklmn") == 1;
        assert words.get("o") == 2;
        assert words.get("букв") == 2;
        Files.deleteIfExists(path);
        System.out.println();
    }
}
