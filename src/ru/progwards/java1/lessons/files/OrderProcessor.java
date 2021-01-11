package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessor {
    Path path;
    int faltas = 0;
    ArrayList<Order> orders = new ArrayList<>();

    /**
     * Constructor for easy Order Processor init.
     *
     * @param startPath is a start path
     */
    public OrderProcessor(String startPath) {
        this.path = Path.of(startPath);
    }

    /**
     * Method for updating all information of folder files
     */
    private void reNewInfo() {
        orders.clear();
        try {
            Files.walkFileTree(this.path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (path.getFileName().toString().endsWith("csv")) {
                        Order order = readFile(path);
                        if (order == null) {
                            faltas++;
                        } else {
                            orders.add(order);
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
    }

    /**
     * Method for read all information from file
     *
     * @param path is an address of the file
     * @return the full-prepared Order
     */
    private Order readFile(Path path) {
        List<String> list;
        Order result = null;
        try {
            list = Files.readAllLines(path);
            ArrayList<OrderItem> items = new ArrayList<>();
            double price = 0;
            boolean cont = true;
            for (String s : list) {
                String[] temp = s.split(", ");
                if (rightOrderItem(temp)) {
                    items.add(new OrderItem(temp[0], Integer.parseInt(temp[1]), Double.parseDouble(temp[2])));
                    price += Integer.parseInt(temp[1]) * Double.parseDouble(temp[2]);
                } else {
                    cont = false;
                    break;
                }
            }
            if (cont) {
                String[] name = path.getFileName().toString().split("-");
                LocalDateTime ldt = LocalDateTime.parse(
                        Files.getAttribute(path, "basic:lastModifiedTime").toString().substring(0, 19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                if (rightFileName(name)) {
                    result = new Order(name[0], name[1], name[2].substring(0, 4), ldt, items, price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method to check there's no any mistake into the order item
     *
     * @param temp String array for add
     * @return is correct
     */
    private boolean rightOrderItem(String[] temp) {
        return temp.length == 3 && temp[0].length() > 0 && temp[1].matches("[0-9]*") && temp[1].length() > 0 &&
                temp[2].matches("[0-9.]*") && temp[2].length() > 0;
    }

    /**
     * Method to check there are no any mistake into the file name
     *
     * @param name prepared array from file name
     * @return is correct
     */
    private boolean rightFileName(String[] name) {
        return name.length == 3 && name[0].length() == 3 && name[1].length() == 6 && name[2].length() == 8;
    }

    /**
     * Check all orders from the time period
     *
     * @param start  is a start time
     * @param finish is a finish time
     * @param shopId id a shop number
     * @return number of mistakes
     */
    public int loadOrders(LocalDate start, LocalDate finish, String shopId) {
        reNewInfo();
        ArrayList<Order> list = new ArrayList<>();
        for (Order value : orders) {
            if ((shopId == null || shopId.equals(value.shopId)) && orderInDateTimeScope(start, finish, value.datetime)) {
                list.add(value);
            }
        }
        this.orders = list;
        return faltas;
    }

    /**
     * Methos for confirm that this datetime is in the period between start and finish
     *
     * @param start    is a date of start
     * @param finish   is a date of finish
     * @param datetime is a date of Order
     * @return is the order in the period
     */
    private boolean orderInDateTimeScope(LocalDate start, LocalDate finish, LocalDateTime datetime) {
        boolean result = true;
        if (start != null) {
            result = datetime.toLocalDate().isAfter(start);
        }
        if (finish != null) {
            result = datetime.toLocalDate().isBefore(finish);
        }
        return result;
    }

    /**
     * Method to prepare the sorted List of Orders of particular shop by time
     *
     * @param shopId is a id of particular shop
     * @return the sorted list
     */
    public List<Order> process(String shopId) {
        reNewInfo();
        if (shopId != null) {
            cleanByShopId(shopId);
        }
        orders.sort((o1, o2) -> {
            int result = 0;
            if (o1.datetime.isBefore(o2.datetime)) {
                result = -1;
            }
            if (o1.datetime.isAfter(o2.datetime)) {
                result = 1;
            }
            return result;
        });
        return orders;
    }

    /**
     * Method for clean the list by shop id
     *
     * @param shopId is a shop id for not-to-clean
     */
    private void cleanByShopId(String shopId) {
        orders = (ArrayList<Order>) orders.stream().filter(x -> x.shopId.equals(shopId)).collect(Collectors.toList());
    }

    /**
     * Method for prepare statistic of shop
     *
     * @return Map with information<shop id, sum>
     */
    public Map<String, Double> statisticsByShop() {
        reNewInfo();
        Map<String, Double> result = new HashMap<>();
        for (Order temp : orders) {
            addIfGoodOrRenovate(result, temp.shopId, temp.sum);

        }
        return result;
    }

    /**
     * Method for prepare Map of statistic
     *
     * @param result      is a Map for add
     * @param info        is a String of key
     * @param forRenovate is a double parameter for value
     */
    private void addIfGoodOrRenovate(Map<String, Double> result, String info, Double forRenovate) {
        if (result.containsKey(info)) {
            result.replace(info, result.get(info) + forRenovate);
        } else {
            result.put(info, forRenovate);
        }
    }

    /**
     * Method for prepare statistic by goods
     *
     * @return prepared Map<name of product, price of all sold>
     */
    public Map<String, Double> statisticsByGoods() {
        reNewInfo();
        Map<String, Double> result = new HashMap<>();
        for (Order order : orders) {
            for (int j = 0; j < order.items.size(); j++) {
                addIfGoodOrRenovate(result, order.items.get(j).googsName,
                        order.items.get(j).price * order.items.get(j).count);
            }
        }
        return result;
    }

    /**
     * Method for prepare statistics by particular day
     *
     * @return Map with date and sum of sold
     */
    public Map<LocalDate, Double> statisticsByDay() {
        reNewInfo();
        Map<LocalDate, Double> result = new HashMap<>();
        for (Order order : orders) {
            LocalDate date = order.datetime.toLocalDate();
            if (result.containsKey(date)) {
                result.replace(date, result.get(date) + order.sum);
            } else {
                result.put(date, order.sum);
            }
        }
        return result;
    }

    /**
     * Special method for create a folder
     *
     * @param path is an address of folder
     * @throws IOException (problems with directory create)
     */
    public static void create(Path path) throws IOException {
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    /**
     * Special method for delete the file(folder) from path
     *
     * @param path is an address for delete
     * @throws IOException (problems with directory delete)
     */
    public static void delete(Path path) throws IOException {
        Files.deleteIfExists(path);
    }

    public static void main(String[] args) throws IOException {
        Path path = Path.of("orderProcessor").toAbsolutePath();
        Path fileOne = Path.of("orderProcessor/AAA-000000-AAAA.csv");
        Path fileTwo = Path.of("orderProcessor/AAB-000001-AAAB.csv");
        Path fileThree = Path.of("orderProcessor/ThisFileIsWrongFile.csv");
        Path fileFour = Path.of("orderProcessor/AAC-000002-AAAC.csv");
        Path fileFive = Path.of("orderProcessor/AAD-000003-AAAD.csv");
        Path fileSix = Path.of("orderProcessor/AAA-000004-AAAE.csv");
        Path fileSeven = Path.of("orderProcessor/AAD-000004-AAAF.csv");
        Path fileEight = Path.of("orderProcessor/AAE-000005-AAAG.csv");
        create(path);
        Files.writeString(fileOne, String.format
                ("Игрушка мягкая “Мишка”, 1, 1500%sПазл “Замок в лесу”, 2, 700%sКнижка “Сказки Пушкина”, 1, 300",
                        System.lineSeparator(), System.lineSeparator()));
        Files.writeString(fileTwo, String.format
                ("Игрушка твёрдая “Тигр”, 2, 1800%sПазл “Замок в пустыне”, 1, 1700%sКнижка “Сказки Гофмана”, 3, 900",
                        System.lineSeparator(), System.lineSeparator()));
        Files.writeString(fileThree, String.format
                ("Файл с ошибкой, 0, 0%sФайл с ошибкой, 0, 0%sФайл с ошибкой, 0, 0",
                        System.lineSeparator(), System.lineSeparator()));
        Files.writeString(fileFour, String.format
                ("Файл с ошибкой, 2, 1800%sПазл “Замок в пустыне”, 1, 1700%sКнижка “Сказки Гофмана”, 3",
                        System.lineSeparator(), System.lineSeparator()));
        Files.writeString(fileFive, String.format("Игрушка мягкая “Зайка”, 1, 2500%sИгрушка Йо-йо, 10, 71.5",
                System.lineSeparator()));
        Files.writeString(fileSix, String.format("Игрушка мягкая “Собака”, 1, 2500%sКнижка “Сказки Андерсена”, 1, 1000.5",
                System.lineSeparator()));
        Files.writeString(fileSeven, "Игрушка мягкая “Рыбка”, 1, 1780");
        Files.writeString(fileEight, String.format("Игрушка мягкая “Собака”, 1, 2500%sКнижка “Сказки Гофмана”, 2, 900",
                System.lineSeparator()));
        Files.setLastModifiedTime(fileFive, FileTime.fromMillis(0));
        OrderProcessor op = new OrderProcessor("orderProcessor");
        if (op.loadOrders(LocalDate.of(2000, 1, 1), LocalDate.now().plusDays(1), null) != 2) {
            throw new AssertionError();
        }
        op.loadOrders(null, LocalDate.of(1970, 1, 2), null);
        assert op.orders.size() == 1;
        op.loadOrders(null, null, "AAA");
        assert op.orders.size() == 2;
        assert op.orders.get(0).shopId.equals("AAA");
        assert op.orders.get(0).sum == 3200.0;
        assert op.orders.get(1).sum == 3500.5;
        op.process("AAD");
        assert op.orders.get(0).datetime.getYear() == 1970;
        HashMap<String, Double> stats = (HashMap<String, Double>) op.statisticsByShop();
        assert stats.get("AAA") == 6700.5;
        assert stats.get("AAB") == 8000.0;
        assert stats.get("AAD") == 4995.0;
        HashMap<String, Double> infoAboutGoods = (HashMap<String, Double>) op.statisticsByGoods();
        assert infoAboutGoods.get("Игрушка мягкая “Собака”") == 5000.0;
        assert infoAboutGoods.get("Книжка “Сказки Гофмана”") == 4500.0;
        assert infoAboutGoods.get("Игрушка Йо-йо") == 715.0;
        HashMap<LocalDate, Double> byDays = (HashMap<LocalDate, Double>) op.statisticsByDay();
        assert byDays.get(LocalDate.of(1970, 1, 1)) == 3215.0;
        delete(fileEight);
        delete(fileSeven);
        delete(fileSix);
        delete(fileFive);
        delete(fileFour);
        delete(fileThree);
        delete(fileTwo);
        delete(fileOne);
        delete(path);
    }
}
