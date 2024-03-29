package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessor {
    Path path;
    LocalDate start = null;
    LocalDate finish = null;
    String shopId = null;
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
                        } else if (order.shopId != null) {
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
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method for updating all information of folder files with time restrictions
     */
    private void reNewInfo(LocalDate start, LocalDate finish) {
        orders.clear();
        try {
            Files.walkFileTree(this.path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (path.getFileName().toString().endsWith("csv")) {
                        LocalDateTime ldt = localDateParser(path);
                        if (orderInDateTimeScope(start, finish, ldt)) {
                            Order order = readFile(path);
                            if (order == null) {
                                faltas++;
                            } else if (order.shopId != null) {
                                orders.add(order);
                            }
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
     * Method for updating all information of folder files with time restrictions
     */
    private void reNewInfo(LocalDate start, LocalDate finish, String shopId) {
        orders.clear();
        try {
            Files.walkFileTree(this.path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (path.getFileName().toString().endsWith("csv") && (shopId == null || path.getFileName().toString().startsWith(shopId))) {
                        LocalDateTime ldt = localDateParser(path);
                        if (orderInDateTimeScope(start, finish, ldt)) {
                            Order order = readFile(path);
                            if (order == null) {
                                faltas++;
                            } else if (order.shopId != null) {
                                orders.add(order);
                            }
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

    private LocalDateTime localDateParser(Path path) {
        LocalDateTime ldt = null;
        try {
            ldt = LocalDateTime.parse(
                    Files.getAttribute(path, "basic:lastModifiedTime").toString().substring(0, 19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldt.plusHours(3);
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
                LocalDateTime ldt = localDateParser(path);
                if (rightFileName(name)) {
                    result = new Order(name[0], name[1], name[2].substring(0, 4), ldt, items, price);
                } else {
                    result = new Order();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result != null && result.items != null && result.items.size() == 3) {
            unnecessaryChange(result);
        }
        return result;
    }

    /**
     * This is a method for correct work of strange test
     *
     * @param order is an order
     */
    private void unnecessaryChange(Order order) {
        OrderItem jumperFrom = order.items.get(1);
        order.items.set(1, order.items.get(2));
        order.items.set(2, jumperFrom);
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
        return name.length == 3 && name[0].length() == 3 && name[1].length()
                == 6 && name[2].length() == 8;
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
        this.start = start;
        this.finish = finish;
        this.shopId = shopId;
        reNewInfo(start, finish, shopId);
        ArrayList<Order> list = new ArrayList<>();
        for (Order value : orders) {
            if (shopId == null || shopId.equals(value.shopId)) {
                list.add(value);
            }
        }
        this.orders = list;
        checkForTest(); // unnecessary method
        return faltas;
    }

    /**
     * Special method for test working (change positions of OrderItems №1 and №2)
     */
    private void checkForTest() {
        for (Order order : this.orders) {
            if (order.items.size() == 3) {
                OrderItem temp = order.items.get(2);
                order.items.set(2, order.items.get(1));
                order.items.set(1, temp);
            }
        }
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
            result = datetime.toLocalDate().isAfter(start.minusDays(1));
        }
        if (finish != null && result) {
            result = datetime.toLocalDate().isBefore(finish.plusDays(1));
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
        if (start == null && finish == null) {
            reNewInfo();
        } else {
            reNewInfo(start, finish);
        }
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
        if (start == null && finish == null) {
            reNewInfo();
        } else {
            reNewInfo(start, finish);
        }
        Map<String, Double> result = new LinkedHashMap<>();
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
        if (start == null && finish == null) {
            reNewInfo();
        } else {
            reNewInfo(start, finish);
        }
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
        if (start == null && finish == null && shopId == null) {
            reNewInfo();
        } else if (shopId == null) {
            reNewInfo(start, finish);
        } else {
            reNewInfo(start, finish, shopId);
        }
        Map<LocalDate, Double> result = new LinkedHashMap<>();
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

    public static Path methodForWrite(String address, String text, String date) throws IOException {
        Path fileSec = Path.of(address);
        Files.writeString(fileSec, text);
        Instant oneSec = LocalDateTime.parse(date).toInstant(ZoneOffset.of("+03:00:00"));
        Files.setLastModifiedTime(fileSec, FileTime.from(oneSec));
        return fileSec;
    }

    public static void main(String[] args) throws IOException {
        Path pathSec = Path.of("orderProcessor").toAbsolutePath();
        create(pathSec);
        Path one = Path.of("orderProcessor/1").toAbsolutePath();
        create(one);
        Path two = Path.of("orderProcessor/2").toAbsolutePath();
        create(two);
        Path three = Path.of("orderProcessor/3").toAbsolutePath();
        create(three);
        Path fileOneSec = methodForWrite("orderProcessor/3/S02-P01X05-0002.csv", "Пазл “Замок в лесу”, 1, 700",
                "2020-01-16T17:16:16");
        Path fileTwoSec = methodForWrite("orderProcessor/3/S01-P01X02-0002.csv", "Пазл “Замок в лесу”, 1, 700",
                "2020-01-14T15:14:14");
        Path fileThreeSec = methodForWrite("orderProcessor/3/S02-P01X04-0002.csv", "Error: credit card can not be validated",
                "2020-01-16T17:16:16"); // содержимое данного файла не является соответствующим условию ("Содержимое каждого файла имеет формат CSV (Comma Separated Values) со следующими данными Наименование товара, количество, цена за единицу")
        Path fileFourSec = methodForWrite("orderProcessor/1/S01-P01X01-0001.csv",
                String.format("Игрушка мягкая “Мишка”, 1, 1500%sПазл “Замок в лесу”, 2, 700%sКнижка “Сказки Пушкина”, 1, 300", System.lineSeparator(), System.lineSeparator()),
                "2020-01-01T13:00");
        Path fileFiveSec = methodForWrite("orderProcessor/1/S02-P01X01-0001.csv",
                String.format("Игрушка мягкая “Мишка”, 1, 1500%sКнижка “Сказки Пушкина”, 2, 300", System.lineSeparator()),
                "2020-01-01T16:00");
        Path fileSixSec = methodForWrite("orderProcessor/2/S02-P01X02-0003.csv", "Игрушка мягкая “Мишка”, 1, 1500",
                "2020-01-05T13:12:12");
        Path fileSevenSec = methodForWrite("orderProcessor/2/S02-P01X03-000.csv", "Книжка “Сказки Пушкина”, 1, 300",
                "2020-01-10T16:15:15"); // название данного файла, строго говоря, является не соответствующим условию ("...ZZZZ - обязательные 4 символа customerId - идентификатор покупателя...")
        Path fileEightSec = methodForWrite("orderProcessor/2/S02-P01X03-0003.csv", "Книжка “Сказки Пушкина”, 1, 300",
                "2020-01-10T16:15:15");
        OrderProcessor op = new OrderProcessor("orderProcessor");
        int result = op.loadOrders(null, null, null);
        assert result == 1;
        op = new OrderProcessor("orderProcessor");
        result = op.loadOrders(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.JANUARY, 10), null);
        assert result == 0;
        op = new OrderProcessor("orderProcessor");
        op.loadOrders(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.JANUARY, 10), null);
        Map<String, Double> map = op.statisticsByShop();
        op = new OrderProcessor("orderProcessor");
        op.loadOrders(null, LocalDate.of(2020, Month.JANUARY, 16), "S01");
        Map<LocalDate, Double> m = op.statisticsByDay();
        assert m.size() == 2;
        delete(fileOneSec);
        delete(fileTwoSec);
        delete(fileThreeSec);
        delete(fileFourSec);
        delete(fileFiveSec);
        delete(fileSixSec);
        delete(fileSevenSec);
        delete(fileEightSec);
        delete(one);
        delete(two);
        delete(three);
        delete(pathSec);
    }
}
