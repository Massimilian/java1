package ru.progwards.java1.lessons;

import org.apache.logging.log4j.core.util.JsonUtils;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException {
        int i = 6;
        String s = "Wor";
        s = switch (i) {
            case 0, 2, 4 -> {
                s = s + "d";
                yield s;
            }
            case 1, 3, 5 -> {
                s = s + "ld";
                yield s;
            }
            default -> "Nothing";
        };
        System.out.println(s); // выведет "Word"/"World"/"Nothing" в зависимости от значения i.
    }
}
