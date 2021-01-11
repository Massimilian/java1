package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FindDuplicates {
    ArrayList<Duple> duples = new ArrayList<>();

    /**
     * Method for find duplicates by name, last change time, size and content.
     *
     * @param startPath is a path when the method is looking for the same files
     * @return list of list of all duplicates
     */
    public List<List<String>> findDuplicates(String startPath) {
        try {
            Path path = Paths.get(startPath).toAbsolutePath().toRealPath();
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (!Files.isDirectory(path)) {
                        try {
                            duples.add(new Duple(path));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                            return FileVisitResult.CONTINUE;
                        }
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
        return catchDuples();
    }

    /**
     * method for catch real duplicates and put them in the list.
     *
     * @return list of lists of duplicates
     */
    private List<List<String>> catchDuples() {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < duples.size() - 1; i++) {
            List<String> list = new ArrayList<>();
            boolean forAdd = false;
            list.add(duples.get(i).getName());
            for (int j = i + 1; j < duples.size(); j++) {
                if (duples.get(i).equals(duples.get(j))) {
                    list.add(duples.get(j).getName());
                    forAdd = true;
                }
            }
            if (forAdd) {
                result.add(list);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        FindDuplicates fd = new FindDuplicates();
        FileTime ft = FileTime.from(Instant.now());
        Path start = Path.of("testThis");
        Path pathOne = Path.of("testThis/Test.txt");
        Path pathDir = Path.of("testThis/test");
        Path pathTwo = Path.of("testThis/test/Test.txt");
        if (!Files.exists(start) || !Files.exists(pathOne) || !Files.exists(pathDir) || !Files.exists(pathTwo)) {
            Files.createDirectory(start);
            Files.createFile(pathOne);
            Files.createDirectory(pathDir);
            Files.createFile(pathTwo);
        }
        Files.setLastModifiedTime(pathOne, ft);
        Files.setLastModifiedTime(pathTwo, ft);
        Duple one = new Duple(pathOne);
        Duple two = new Duple(pathTwo);
        assert one.equals(two);
        List<List<String>> list = fd.findDuplicates("testThis");
        assert list.size() == 1;
        assert list.get(0).get(0).contains("Test.txt");
        Files.deleteIfExists(pathTwo);
        Files.deleteIfExists(pathDir);
        Files.deleteIfExists(pathOne);
        Files.deleteIfExists(start);
    }
}
