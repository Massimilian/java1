package ru.progwards.java1.lessons;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;

public class Test {
    String createFolder(String name) {
        String s = Paths.get(".").toAbsolutePath().normalize().resolve(name).toString(); // создаём путь к текущей папке
        File file = new File(s);
        if (!file.exists()) {
            file.mkdir();
        }
        return Paths.get("..").toAbsolutePath().normalize().toString();
    }

    boolean replaceF(String name) {
        try {
            Path path = Path.of(name);
            String s = Files.readString(path);
            s = s.replaceAll("[F]", "f");
            Files.writeString(path, s);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        char[][] test = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                test[i][j] = '*';
            }
        }
        int x = 0;
        int y = 0;
        int count = 3;
        while (y < 10) {
            if (test[x][y] == '0') {
                System.out.println("Position " + x + " " + y + " skipped.");
            }
            test[x][y] = '0';
            x += count;
            if (x >= 10) {
                x -= 10;
                y++;
            }
        }
        x = 0;
        y = 0;
        count--;
        while (y < 10) {
            if (test[x][y] == '0') {
                System.out.println("Position " + x + " " + y + " skipped.");
            }
            test[x][y] = '0';
            x += count;
            if (x >= 10) {
                x -= 10;
                y++;
            }
        }
        x = 0;
        y = 0;
        count--;
        while (y < 10) {
            if (test[x][y] == '0') {
                System.out.println("Position " + x + " " + y + " skipped.");
            }
            test[x][y] = '0';
            x += count;
            if (x >= 10) {
                x -= 10;
                y++;
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(test[i][j] + " ");
            }
            System.out.println();
        }
    }

}
