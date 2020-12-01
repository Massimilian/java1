package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for work with two sets
 */
public class SetOperations {
    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> set = new HashSet(set1);
        set.addAll(set2);
        return set;
    }

    /**
     * Method to intersect two Sets
     *
     * @param set1 for intersect
     * @param set2 for intersect
     * @return result of intersection
     */
    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> set = new HashSet<>(set1);
        set.retainAll(set2);
        return set;
    }

    /**
     * Method to find the difference between two Sets
     *
     * @param set1 for find the difference
     * @param set2 for find the difference
     * @return set that is a difference between two sets
     */
    public static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> set = new HashSet<>(set1);
        set.removeAll(set2);
        return set;
    }

    /**
     * Method to find the symmetric difference between sets
     *
     * @param set1 for find the difference
     * @param set2 for find the difference
     * @return the symmetric difference between two sets
     */
    public static Set<Integer> symDifference(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> set = union(set1, set2);
        set.removeAll(intersection(set1, set2));
        return set;
    }

    public static void main(String[] args) {
        HashSet<Integer> set1 = new HashSet<>(List.of(0, 1, 2, 3, 4));
        HashSet<Integer> set2 = new HashSet<>(List.of(3, 4, 5, 6, 7));
        assert union(set1, set2).equals(new HashSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7)));
        assert intersection(set1, set2).equals(new HashSet<>(List.of(3, 4)));
        assert difference(set1, set2).equals(new HashSet<>(List.of(0, 1, 2)));
        assert symDifference(set1, set2).equals(new HashSet<>(List.of(0, 1, 2, 5, 6, 7)));
    }
}
