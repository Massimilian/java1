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

public class UsageFrequency {
    private String text;

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

    public Map<String, Integer> getWords() {
        Map<String, Integer> words = new HashMap<>();
        String[] arrWord = text.split("[^A-Za-zА-Яа-я0-9]+");
        for (int i = 0; i < arrWord.length; i++) {
            if (!arrWord[i].equals("")) {
                if (words.containsKey(arrWord[i].toLowerCase())) {
                    words.replace(arrWord[i].toLowerCase(), words.get(arrWord[i].toLowerCase()) + 1);
                } else {
                    words.put(arrWord[i].toLowerCase(), 1);
                }
            }
        }
        return words;
    }


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

        uf.putSomeTestInfo(test, "   asd ");
        uf.processFile(test);
        Map<String, Integer> mapTest = uf.getWords();
        System.out.println(mapTest);

//        uf.putSomeTestInfo(test, "abC Abc defghi jklmn jklmno o pqrstuvwxyz o O; \nпроверка*?:русских;№букв+=-букв");
//        uf.processFile(test);
        wikiTrainUf.processFile(wikiTrainTest);
        wikiTestUf.processFile(wikiTestTest);
//        Map<Character, Integer> map = uf.getLetters();
//        Map<String, Integer> words = uf.getWords();
        Map<Character, Integer> wikiMap = wikiTrainUf.getLetters();
        Map<String, Integer> wikiWords = wikiTestUf.getWords();
        System.out.println(wikiMap);
//        assert map.get('a') == 1;
//        assert map.get('b') == 2;
//        assert map.get('j') == 2;
//        assert map.get('у') == 3;
//        assert words.get("abc") == 2;
//        assert words.get("jklmn") == 1;
//        assert words.get("o") == 3;
//        assert words.get("букв") == 2;
        Files.deleteIfExists(path);
        System.out.println();
    }
}
