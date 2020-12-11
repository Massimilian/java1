package ru.progwards.java1.lessons.maps;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SalesInfo {
    HashSet<Good> goods = new HashSet<>();

    public int loadOrders(String fileName) {
        FileReader fr = null;
        int count = 0;
        try {
            fr = new FileReader(fileName);
            Scanner scanner = new Scanner(fr);
            while (scanner.hasNextLine()) {
                String[] good = scanner.nextLine().split(", ");
                if (good.length == 4) {
                    try {
                        goods.add(new Good(good[0], good[1], Integer.parseInt(good[2]), Double.parseDouble(good[3])));
                        count++;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, Double> getGoods() {
        Map<String, Double> map = new TreeMap<>();
        Iterator<Good> iterator = goods.iterator();
        while (iterator.hasNext()) {
            Good temp = iterator.next();
            if (map.containsKey(temp.getName())) {
                map.replace(temp.getName(), map.get(temp.getName()) + temp.getSum());
            } else {
                map.put(temp.getName(), temp.getSum());
            }
        }
        return map;
    }

    public Map<String, AbstractMap.SimpleEntry<Double, Integer>> getCustomers() {
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> owners = new TreeMap<>();
        Iterator<Good> iterator = goods.iterator();
        while (iterator.hasNext()) {
            Good temp = iterator.next();
            if (!owners.containsKey(temp.getOwner())) {
                owners.put(temp.getOwner(), new AbstractMap.SimpleEntry(temp.getSum(), 1));
            } else {
                AbstractMap.SimpleEntry<Double, Integer> amse = owners.get(temp.getOwner());
                owners.replace(temp.getOwner(), new AbstractMap.SimpleEntry(amse.getKey() + temp.getSum(), amse.getValue() + 1));
            }
        }
        return owners;
    }

    private void putSomeTestInfo(String file, String text) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(text);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        SalesInfo si = new SalesInfo();
        SalesInfo siTwo = new SalesInfo();
        String test = "test.txt";
        String testTwo = "test2.txt";
        Path path = Paths.get(testTwo);
        Path pathTwo = Paths.get(test);
        Files.deleteIfExists(path);
        Files.createFile(path);
        Files.deleteIfExists(pathTwo);
        Files.createFile(pathTwo);
        si.putSomeTestInfo(test, "Иванов Сергей, iPhone 10X, 2, 150000\n" +
                "Петрова Анна, наушники JBL, 2, 7000 \n" +
                "Иванов Сергей, чехол для iPhone, 1, 1000\n" +
                "Петрова Анна, пакет пластиковый, 1, 3 \n" +
                "Радж Кумар, батарейка ААА, 1, 150\n" +
                "Михаил Цикло, iPhone 10X, 1, 75000\n" +
                "Радж Кумар, пакет пластиковый, 1, 3 \n" +
                "Михаил Цикло, пакет пластиковый, 1, 3\n" +
                "Иванов Сергей, стекло защитное, 1, 1000\n" +
                "Василий Пупкин, спички, 10, 10\n" +
                "Неправильная строка, Прямо, совсем, неправильная\n" +
                "Ещё одна, почти, 10, 10, правильная");
        si.putSomeTestInfo(testTwo, "Петрова Анна, наушники JBL, 2, 7000\n" +
                "Василий Пупкин, 10, 10, спички\n" +
                "Василий Пупкин, спички, 10, 10, 100\n" +
                "10, Василий Пупкин\n" +
                "Михаил Цикло, iPhone 10X, 1, 75000\n" +
                "Василий Пупкин, спички, 10, 10, 100");
        assert siTwo.loadOrders(testTwo) == 2;
        assert si.loadOrders(test) == 10;
        Map<String, Double> map = si.getGoods();
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> ownersMap = si.getCustomers();
        Map<String, AbstractMap.SimpleEntry<Double, Integer>> ownersMapTwo = siTwo.getCustomers();
        assert ownersMapTwo.get("Петрова Анна").getValue() == 1;
        assert map.size() == 7;
        assert map.get("iPhone 10X") == 225000.0;
        assert map.get("наушники JBL") == 7000.0;
        assert ownersMap.size() == 5;
        String[] names = {"Василий Пупкин", "Иванов Сергей", "Михаил Цикло", "Петрова Анна", "Радж Кумар"};
        int count = 0;
        for (var entry : ownersMap.entrySet()) {
            assert names[count++].equals(entry.getKey());
        }
        assert ownersMap.get("Василий Пупкин").getValue() == 1;
        Files.deleteIfExists(path);
        Files.deleteIfExists(pathTwo);
    }
}
