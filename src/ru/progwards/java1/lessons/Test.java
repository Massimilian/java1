package ru.progwards.java1.lessons;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.util.Collections;
import java.util.Date;
import java.util.Stack;
import java.util.StringTokenizer;

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

    String swapWords(String sentance) {
        StringTokenizer stn = new StringTokenizer(sentance, " .,-!\n");
        String result = "";
        while(stn.hasMoreElements()) {
            String one = stn.nextToken();
            String two = "";
            if (stn.hasMoreElements()) {
                two = stn.nextToken();
            }
            result += two.equals("")? one + " " : two + " " + one + " ";
        }
        return result.substring(0, result.length() - 1);
    }

    public static void main(String[] args) throws IOException {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt.plusHours(3));
    }
}
