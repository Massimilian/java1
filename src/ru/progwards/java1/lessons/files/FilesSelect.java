package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FilesSelect {
    List<String> txtFiles = new ArrayList<>();

    /**
     * Method for put values in the txtFiles, prepare them to put into the new folder and put it
     *
     * @param isFolder  is a folder with files
     * @param outFolder is a folder when we'll put the files
     * @param keys      is possible folder's names and keys to find
     */
    public void selectFiles(String isFolder, String outFolder, List<String> keys) {
        this.addFiles(isFolder);
        for (String txtFile : txtFiles) {
            checkFile(txtFile, keys, outFolder);
        }
        txtFiles.clear();
    }

    /**
     * Method for prepare and check files in a new folder (or new folders)
     *
     * @param s    is a path's name
     * @param keys are the keys for check
     * @param out  is a name of path for add
     */
    private void checkFile(String s, List<String> keys, String out) {
        Path path = Path.of(s);
        try {
            ArrayList<String> word = this.hasValues(keys, Files.readString(path));
            if (!word.isEmpty()) {
                this.copy(path, out, word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for copy all correct files into a new folder(s)
     *
     * @param path    is a base path
     * @param out     is a name of path for add
     * @param dirName is a list with values to prepare a folder
     */
    private void copy(Path path, String out, ArrayList<String> dirName) {
        for (String s : dirName) {
            try {
                Path main = Path.of(out).toAbsolutePath();
                this.create(main);
                Path folder = Path.of(String.format("%s/%s", main, s));
                this.create(folder);
                Path dest = Path.of(String.format("%s/%s", folder, path.getFileName()));
                Files.copy(path, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method for create a new directory with a special name
     *
     * @param main is a new address
     * @throws IOException (if cannot create directory)
     */
    private void create(Path main) throws IOException {
        if (!Files.exists(main)) {
            Files.createDirectory(main);
        }
    }

    /**
     * Method for check the words equals with keys of main method
     *
     * @param keys are keys from main method
     * @param text is a text for check
     * @return checked words
     */
    private ArrayList<String> hasValues(List<String> keys, String text) {
        ArrayList<String> word = new ArrayList<>();
        for (String key : keys) {
            if (text.contains(key)) {
                word.add(key);
            }
        }
        return word;
    }

    /**
     * Method for add all files text content info the list (txtFiles)
     *
     * @param folder is a path from which we'll take the content.
     */
    public void addFiles(String folder) {
        try {
            Path path = Paths.get(folder).toAbsolutePath().toRealPath();
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (!Files.isDirectory(path) && path.getFileName().toString().endsWith(".txt")) {
                        txtFiles.add(path.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Special method for prepare test area
     *
     * @param path is a path for test files
     * @param text is a text for fill
     * @throws IOException (when cannot fill with text)
     */
    public void fillWithText(Path path, String text) throws IOException {
        Files.writeString(path, text);
    }

    public static void main(String[] args) throws IOException {
        ArrayDeque<Path> paths = new ArrayDeque<>();
        Path testOne = Path.of("testThis/Test.txt");
        Path testTwo = Path.of("testThis/fileForAdd.txt");
        Path testThree = Path.of("testThis/test/TestCopy.txt");
        Path testFour = Path.of("testThis/test/AnotherTest.txt");
        paths.addLast(Path.of("testThis"));
        paths.addLast(Path.of("testThis/test"));
        paths.addLast(testOne);
        paths.addLast(Path.of("testThis/Wrong.bak"));
        paths.addLast(testTwo);
        paths.addLast(testThree);
        paths.addLast(testFour);
        paths.addLast(Path.of("testThis/test/Wrong.iml"));
        Iterator<Path> iterator = paths.iterator();
        Path temp;
        for (int i = 0; i < 2; i++)
            if (!Files.exists(temp = iterator.next())) {
                Files.createDirectory(temp);
            }
        while (iterator.hasNext()) {
            if (!Files.exists(temp = iterator.next())) {
                Files.createFile(temp);
            }
        }
        FilesSelect fs = new FilesSelect();
        fs.fillWithText(testOne, "one, two");
        fs.fillWithText(testTwo, "two, three");
        fs.fillWithText(testThree, "three, zero");
        fs.fillWithText(testFour, "zero, one");
        ArrayList<String> list = new ArrayList<>(Collections.singletonList("one"));
        fs.selectFiles("testThis", "results", list);
        assert Files.exists(Path.of("results/one/AnotherTest.txt").toAbsolutePath());
        assert Files.exists(Path.of("results/one/Test.txt").toAbsolutePath());
        list = new ArrayList<>(Arrays.asList("zero", "one"));
        fs.selectFiles("testThis", "newResultsOfWorkingProgramWithAVeryLongName", list);
        assert Files.exists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/one/AnotherTest.txt"));
        assert Files.exists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/one/Test.txt"));
        assert Files.exists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/zero/AnotherTest.txt"));
        assert Files.exists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/zero/TestCopy.txt"));
        Files.deleteIfExists(Path.of("results/one/AnotherTest.txt"));
        Files.deleteIfExists(Path.of("results/one/Test.txt"));
        Files.deleteIfExists(Path.of("results/one"));
        Files.deleteIfExists(Path.of("results"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/one/AnotherTest.txt"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/one/Test.txt"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/zero/AnotherTest.txt"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/zero/TestCopy.txt"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/one"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName/zero"));
        Files.deleteIfExists(Path.of("newResultsOfWorkingProgramWithAVeryLongName"));
        while (!paths.isEmpty()) {
            Files.deleteIfExists(paths.pollLast());
        }
    }
}
