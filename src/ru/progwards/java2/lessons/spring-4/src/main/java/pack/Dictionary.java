package pack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {
    private final String startAddress = "src/main/resources";
    private String dictName;
    private ArrayList<Word> words;
    private Scanner scanner = new Scanner(System.in);
    private boolean deleteExit = false;

    @PostConstruct
    public void init() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Список словарей:");
        File file = new File(startAddress);
        String[] fileNames = file.list();
        for (int i = 0; i < fileNames.length; i++) {
            System.out.println(fileNames[i]);
        }
        System.out.println("Введите имя Вашего словаря либо назовите новый словарь.");
        String name = scanner.nextLine();
        this.dictName = name.endsWith(".txt") ? name : name + ".txt";
        Path path = Paths.get(startAddress + "/" + this.dictName);
        if (lookNames(this.dictName, fileNames)) {
            String json = Files.readString(path);
            if (json.equals("")) {
                this.words = new ArrayList<>();
            } else {
                Type type = new TypeToken<ArrayList<Word>>() {
                }.getType();
                this.words = new Gson().fromJson(json, type);
            }
        } else {
            Files.createFile(path);
            this.words = new ArrayList<>();
        }
    }

    @PreDestroy
    public void destroy() throws IOException {
        if (!deleteExit) {
            String json = new Gson().toJson(words);
            Files.writeString(Path.of(startAddress + "/" + this.dictName), json);
        }
    }

    private boolean lookNames(String name, String[] fileNames) {
        boolean result = false;
        for (int i = 0; i < fileNames.length; i++) {
            if (name.equals(fileNames[i])) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean work() throws IOException {
        boolean toCont = true;
        boolean incorrectAnswer = true;
        while (incorrectAnswer) {
            System.out.println("1. Изучение (с русского). 2. Изучение (с иностранного). 3. Новое слово. 4. Удалить словарь. 5. Выйти из программы");
            String answer = this.scanner.nextLine();
            switch (answer) {
                case "1":
                    this.learn(true);
                    incorrectAnswer = false;
                    break;
                case "2":
                    this.learn(false);
                    incorrectAnswer = false;
                    break;
                case "3":
                    this.newWord();
                    incorrectAnswer = false;
                    break;
                case "4":
                    if (this.forRealDelete()) {
                        Path path = Paths.get(startAddress + "/" + dictName);
                        Files.deleteIfExists(path);
                        toCont = false;
                        deleteExit = true;
                    }
                    incorrectAnswer = false;
                    break;
                case "5":
                    toCont = false;
                    incorrectAnswer = false;
                    break;
                default:
                    System.out.println("Ответ некорректный. Попробуйте ещё раз");
            }
        }
        return toCont;
    }

    private void learn(boolean fromRu) {
        Random random = new Random();
        boolean toCont = this.words.size() != 0;
        while(toCont) {
            boolean isForDelete = false;
            boolean isEdited = false;
            Word word = this.words.get(random.nextInt(this.words.size()));
            System.out.println("Введите перевод слова '" + (fromRu? word.getName() : word.getTranslate()) + "'");
            boolean correct = checkWord(scanner.nextLine(), word, fromRu);
            if (correct) {
                word.setCount(word.getCount() - 1);
                System.out.println("Правильно!");
                String res = word.getNote().isEmpty() ? "отсутствует" : word.getNote();
                System.out.println("Примечание - " + res);
                if (word.getCount() == 0) {
                    System.out.printf("ВНИМАНИЕ! Слово '%s' (%s) удалено!%s", word.getName(), word.getTranslate(), System.lineSeparator());
                    isForDelete = true;
                    this.words.remove(word);
                }
            } else {
                word.setCount(10);
                System.out.println("Неправильно!");
            }
            if (!isEdited) {
                System.out.println(word.getName() + "  ---  " + word.getTranslate());
                if (!isForDelete) {
                    this.renovateWords(word);
                }
            }
            if (!isForDelete) {
                System.out.println("Хотите отредактировать (наберите да, для продолжения - enter)?");
                if (scanner.nextLine().equals("да")) {
                    this.delAndPut(word);
                }
            }
            System.out.println("Для выхода нажмите 'всё', для продолжения - enter.");
            toCont = !scanner.nextLine().equals("всё");
        }
    }

    private void delAndPut(Word word) {
        this.words.remove(word);
        this.newWord();
    }

    private boolean checkWord(String nextLine, Word word, boolean fromRu) {
        boolean correct;
        if (fromRu) {
            correct = nextLine.toLowerCase(Locale.ROOT).equals(word.getTranslate().toLowerCase(Locale.ROOT));
        } else {
            correct = word.getName().toLowerCase(Locale.ROOT).contains(nextLine.toLowerCase(Locale.ROOT));
        }
        return correct;
    }

    private boolean forRealDelete() {
        System.out.println("Вы уверены, что хотите удалить словарь (если да - наберите слово 'удалить')?");
        return scanner.nextLine().equals("удалить");
    }

    private void newWord() {
        System.out.println("Введите значение русскими буквами:");
        String name = scanner.nextLine();
        System.out.println("Введите перевод слова на иностранный язык:");
        String translate = scanner.nextLine();
        System.out.println("Введите примечание к слову:");
        String note = scanner.nextLine();
        Word word = new Word(name, translate, note);
        this.renovateWords(word);
    }

    private void renovateWords(Word word) {
        if (this.words.contains(word)) {
            this.words.remove(word);
        }
        this.words.add(word);
    }
}
