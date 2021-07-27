import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Class for test Binary tree and Avl tree
 */
public class TreeTester {
    public static int[] numbers = {10, 300, 500, 1000, 2000};
    public static String sep = System.lineSeparator();

    private static ArrayList<Integer> fillList(int number, boolean needShuffle) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(i);
        }
        if (needShuffle) {
            Collections.shuffle(result);
        }
        return result;
    }

    /**
     * Adding into Avl-tree
     *
     * @param at       is tree
     * @param iterator are values
     * @param num is number of values
     * @throws Exception
     */
    private static void addAvl(AvlTree<Integer, Integer> at, Iterator<Integer> iterator, int num, String type, boolean forPrint) throws Exception {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            int get = iterator.next();
            at.put(new AvlTreeLeaf<>(get, get));
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        if (forPrint) {
            System.out.printf("The using time of adding %d %s elements for Avl tree is %s seconds.%s", num, type, time, sep);
        }
    }

    /**
     * Adding into Binary tree
     *
     * @param bt   is tree
     * @param iterator are values
     * @param num is number of values
     * @throws Exception
     */
    private static void addBin(BinaryTree<Integer, Integer> bt, Iterator<Integer> iterator, int num, String type, boolean forPrint) throws Exception {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            int get = iterator.next();
            bt.add(new TreeLeaf<>(get, get));
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        if (forPrint) {
            System.out.printf("The using time of adding %d %s elements for Binary tree is %s seconds.%s", num, type, time, sep);
        }
    }

    /**
     * Delete from Avl-tree
     *
     * @param at   is tree
     * @param iterator are values
     * @param num is number of values
     */
    private static void delAvl(AvlTree<Integer, Integer> at, Iterator<Integer> iterator, int num, String type, boolean forPrint) {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            int get = iterator.next();
            at.delete(get);
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        if (forPrint) {
            System.out.printf("The using time of deleting %d %s elements for Avl tree is %s seconds.%s", num, type, time, sep);
        }
    }

    /**
     * Delete from Binary tree
     *
     * @param at   is tree
     * @param iterator are values
     * @param num is number of values
     * @throws Exception
     */
    private static void delBin(BinaryTree<Integer, Integer> at, Iterator<Integer> iterator, int num, String type, boolean forPrint) throws Exception {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            int get = iterator.next();
            at.delete(get);
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        if (forPrint) {
            System.out.printf("The using time of deleting %d %s elements for Binary tree is %s seconds.%s", num, type, time, sep);
        }
    }

    /**
     * Method for find the value
     * @param at is Avl tree when we need to find
     * @param iterator are values
     * @param num is number of values
     * @param sorted is sorted or no
     */
    private static void findAvl(AvlTree<Integer, Integer> at, Iterator<Integer> iterator, int num, boolean sorted) {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            int get = iterator.next();
            at.find(get);
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        System.out.printf("The using time of finding %d %s elements for Avl tree is %s seconds.%s", num, sorted ? "sorted" : "unsorted", time, sep);
    }

    /**
     * Method for find the value
     * @param bt is Binary tree when we need to find
     * @param iterator are values
     * @param num is number of values
     * @param sorted is sorted or no
     */
    private static void findBin(BinaryTree<Integer, Integer> bt, Iterator<Integer> iterator, int num, boolean sorted) {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            int get = iterator.next();
            bt.find(get);
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        System.out.printf("The using time of finding %d %s elements for Binary tree is %s seconds.%s", num, sorted ? "sorted" : "unsorted", time, sep);
    }

    /**
     * Method for prepare the Set of words
     * @param address from where we'll take all words
     * @param needOrder is need or no
     * @return prepared Set
     * @throws IOException
     */
    private static Set takeWords(String address, boolean needOrder) throws IOException {
        Set<String> result;
        if (!needOrder) {
            result = new HashSet<>();
        } else {
            result = new TreeSet<>();
        }
        Path path = Paths.get(address);
        if (Files.exists(path)) {
            String temp = Files.readString(path);
            String[] semiResult = temp.split(" ");
            Collections.addAll(result, semiResult);
        }
        return result;
    }

    /**
     * Method for add or delete the values from the Avl tree
     * @param at is a tree from where we'll add/delete the value
     * @param iterator are values
     * @param isAdd is add or delete
     * @param isSorted is sorted or no
     * @throws Exception
     */
    private static void addOrDelWordsAvl(AvlTree<String, String> at, Iterator<String> iterator, boolean isAdd, boolean isSorted) throws Exception {
        String get;
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            get = iterator.next();
            if (isAdd) {
                at.put(get, get);
            } else {
                at.delete(get);
            }
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        System.out.printf("The using time of %s word elements for Avl tree is %s seconds.%s", isAdd ? "adding" : "deleting", isSorted ? "sorted" : "unsorted", time, sep);
    }

    /**
     * Method for add or delete the values from the Binary tree
     * @param bt is a tree from where we'll add/delete the value
     * @param iterator are values
     * @param isAdd is add or delete
     * @param isSorted is sorted or no
     * @throws Exception
     */
    private static void addOrDelWordsBin(BinaryTree<String, String> bt, Iterator<String> iterator, boolean isAdd, boolean isSorted) throws Exception {
        String get;
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            get = iterator.next();
            if (isAdd) {
                bt.add(new TreeLeaf<>(get, get));
            } else {
                bt.delete(get);
            }
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        System.out.printf("The using time of %s word elements for Binary tree is %s seconds.%s", isAdd ? "adding" : "deleting", isSorted ? "sorted" : "unsorted", time, sep);
    }

    /**
     * Method for find the words in the Avl tree
     * @param at is a tree
     * @param iterator are values
     * @param isSorted is sorted or no
     */
    private static void findWordsAvl(AvlTree<String, String> at, Iterator<String> iterator, boolean isSorted) {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            at.find(iterator.next());
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        System.out.printf("The using time of finding %s word elements for Avl tree is %s seconds.%s", isSorted ? "sorted" : "unsorted", time, sep);
    }

    /**
     * Method for find the words in the Binary tree
     * @param bt is a tree
     * @param iterator are values
     * @param isSorted is sorted or no
     */
    private static void findWordsBin(BinaryTree<String, String> bt, Iterator<String> iterator, boolean isSorted) {
        Instant start = Instant.now();
        while (iterator.hasNext()) {
            bt.find(iterator.next());
        }
        Instant finish = Instant.now();
        Duration dur = Duration.between(start, finish);
        String time = dur.getSeconds() + ":" + dur.getNano();
        System.out.printf("The using time of finding %s word elements for Binary tree is %s seconds.%s", isSorted ? "sorted" : "unsorted", time, sep);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("*** Welcome to the tree tester! ***");
        AvlTree<Integer, Integer> at = new AvlTree<>();
        BinaryTree<Integer, Integer> bt = new BinaryTree<>();
        for (int i = 0; i < numbers.length; i++) {
            System.out.printf("Work with %d elements:%s", numbers[i], sep);
            ArrayList<Integer> list = fillList(numbers[i], true);
            addAvl(at, list.iterator(), list.size(), "random", true);
            addBin(bt, list.iterator(), list.size(), "random", true);
            findAvl(at, list.iterator(), list.size(), false);
            findBin(bt, list.iterator(), list.size(), false);
            delAvl(at, list.iterator(), list.size(), "random", true);
            delBin(bt, list.iterator(), list.size(), "random", true);
            list = fillList(numbers[i], false);
            addAvl(at, list.iterator(), list.size(), "\'from zero to max\'", true);
            addBin(bt, list.iterator(), list.size(), "\'from zero to max\'", true);
            findAvl(at, list.iterator(), list.size(), true);
            findBin(bt, list.iterator(), list.size(), true);
            delAvl(at, list.iterator(), list.size(), "\'from zero to max\'", true);
            delBin(bt, list.iterator(), list.size(), "\'from zero to max\'", true);
        }
        System.out.printf("%s*** Work with String values. ***%s", sep, sep);
        AvlTree atTwo = new AvlTree();
        BinaryTree btTwo = new BinaryTree();
        HashSet<String> words = (HashSet<String>) takeWords("src/ru/progwards/java2/lessons/trees/src/main/resources/wiki.train.tokens", false);
        addOrDelWordsAvl(atTwo, words.iterator(), true, false);
        addOrDelWordsBin(btTwo, words.iterator(), true, false);
        findWordsAvl(atTwo, words.iterator(), false);
        findWordsBin(btTwo, words.iterator(), false);
        addOrDelWordsAvl(atTwo, words.iterator(), false, false);
        addOrDelWordsBin(btTwo, words.iterator(), false, false);
        TreeSet<String> sortedWords = (TreeSet<String>) takeWords("src/ru/progwards/java2/lessons/trees/src/main/resources/wiki.train.tokens", true);
        addOrDelWordsAvl(atTwo, words.iterator(), true, true);
        addOrDelWordsBin(btTwo, words.iterator(), true, true);
        findWordsAvl(atTwo, words.iterator(), true);
        findWordsBin(btTwo, words.iterator(), true);
        addOrDelWordsAvl(atTwo, words.iterator(), false, true);
        addOrDelWordsBin(btTwo, words.iterator(), false, true);
    }
}
