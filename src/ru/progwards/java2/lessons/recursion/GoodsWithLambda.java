package ru.progwards.java2.lessons.recursion;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Special class for group together different groops
 */
public class GoodsWithLambda {
    List<Goods> goods;

    public void setGoods(List<Goods> list) {
        this.goods = list;
    }

    /**
     * Sorting by good's name
     * @return sorted list
     */
    public List<Goods> sortByName() {
        return goods.stream().sorted(Comparator.comparing(o -> o.name)).collect(Collectors.toList());
    }

    /**
     * Sorting by good's number
     * @return sorted list
     */
    public List<Goods> sortByNumber() {
        return goods.stream().sorted(Comparator.comparing(o -> o.number.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * Sorting by part number
     * @return sorted list
     */
    public List<Goods> sortByPartNumber() {
        return goods.stream().sorted(Comparator.comparing(o -> o.number.toLowerCase().substring(0, 3))).collect(Collectors.toList());
    }

    /**
     * Sorting by availability and number
     * @return sorted list
     */
    public List<Goods> sortByAvailabilityAndNumber() {
        Comparator<Goods> comparator = Comparator.comparing(g -> g.available);
        comparator = comparator.thenComparing(g -> g.number.toLowerCase());
        return goods.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * Sorting by final date
     * @param date is a date of final
     * @return sorted list
     */
    public List<Goods> expiredAfter(Instant date) {
        return goods.stream().sorted(Comparator.comparing(o -> o.expired)).filter(o -> o.expired.isAfter(date)).collect(Collectors.toList());
    }

    /**
     * Sorting and filtering by availabality
     * @param count is a number for filter
     * @return sorted and filtered list
     */
    public List<Goods> сountLess(int count) {
        return goods.stream().sorted(Comparator.comparing(o -> o.available)).filter(o -> o.available < count).collect(Collectors.toList());
    }

    /**
     * Sorteing and filtering by to counts
     * @param count1 "Before"
     * @param count2 "After"
     * @return sorted and filtered list
     */
    public List<Goods> сountBetween(int count1, int count2) {
        return goods.stream().sorted(Comparator.comparing(o -> o.available)).filter(o -> o.available > count1 && o.available < count2).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Goods gone = new Goods("One", "Ccc", 1, 1.1, Instant.now().plusSeconds(111));
        Goods gtwo = new Goods("Two", "bbB", 2, 2.2, Instant.now().plusSeconds(222));
        Goods gthree = new Goods("Three", "dddd", 3, 3.3, Instant.now().plusSeconds(333));
        Goods gthreeMore = new Goods("Three", "AaA", 1, 4.4, Instant.now().minusSeconds(444));
        GoodsWithLambda gwl = new GoodsWithLambda();
        gwl.setGoods(List.of(gone, gtwo, gthree, gthreeMore));
        assert gwl.goods.size() == 4;
        assert gwl.sortByName().get(0).name.equals("One");
        assert gwl.sortByName().get(1).name.equals("Three");
        assert gwl.sortByName().get(2).name.equals("Three");
        assert gwl.sortByName().get(3).name.equals("Two");
        assert gwl.sortByNumber().get(0).number.equals("AaA");
        assert gwl.sortByNumber().get(1).number.equals("bbB");
        assert gwl.sortByNumber().get(2).number.equals("Ccc");
        assert gwl.sortByNumber().get(3).number.equals("dddd");
        assert gwl.sortByPartNumber().get(0).number.equals("AaA");
        assert gwl.sortByPartNumber().get(1).number.equals("bbB");
        assert gwl.sortByPartNumber().get(2).number.equals("Ccc");
        assert gwl.sortByPartNumber().get(3).number.equals("dddd");
        assert gwl.sortByAvailabilityAndNumber().get(0).name.equals("Three");
        assert gwl.sortByAvailabilityAndNumber().get(0).available == 1;
        assert gwl.sortByAvailabilityAndNumber().get(1).name.equals("One");
        assert gwl.sortByAvailabilityAndNumber().get(2).name.equals("Two");
        assert gwl.sortByAvailabilityAndNumber().get(3).name.equals("Three");
        assert gwl.sortByAvailabilityAndNumber().get(3).available == 3;
        assert gwl.expiredAfter(Instant.now()).get(0).name.equals("One");
        assert gwl.expiredAfter(Instant.now()).get(1).name.equals("Two");
        assert gwl.expiredAfter(Instant.now()).get(2).name.equals("Three");
        assert gwl.сountLess(2).size() == 2;
        assert gwl.сountLess(2).get(0).available == gwl.сountLess(2).get(1).available;
        assert gwl.сountBetween(1, 3).get(0).name.equals("Two");
    }
}