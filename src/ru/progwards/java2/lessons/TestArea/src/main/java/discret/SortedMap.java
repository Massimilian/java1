package discret;

import java.util.Map;

public class SortedMap<K extends Comparable<K>, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    Entry<K, V> first = null;

    public V get(K key) {
        V result = null;
        SortedMap.Entry<K, V> temp = first;
        while (temp != null && key.compareTo(temp.key) > 0) {
            temp = temp.next;
        }
        if (temp != null && temp.key.compareTo(key) == 0) {
            result = temp.value;
        }
        return result;
    }

    public V put(K key, V value) {
        return this.put(key, value, false);
    }

    public V put(K key, V value, boolean isAbsent) {
        if (isEmpty()) {
            this.first = new SortedMap.Entry<>(key, value);
        } else if (first.key.compareTo(key) > 0) {
            SortedMap.Entry<K, V> entry = new SortedMap.Entry<>(key, value);
            entry.next = this.first;
            this.first = entry;
        } else {
            SortedMap.Entry<K, V> temp = first;
            SortedMap.Entry<K, V> underTemp = null;
            while(temp != null && temp.key.compareTo(key) < 0) {
                underTemp = temp;
                temp = temp.next;
            }
            if (temp == null) {
                temp = new SortedMap.Entry<>(key, value);
                underTemp.next = temp;
            }
            if (temp.key.compareTo(key) == 0) {
                if (!isAbsent) {
                    temp.value = value;
                } else {
                    value = temp.value;
                }
            } else {
                SortedMap.Entry<K, V> entry = new SortedMap.Entry<>(key, value);
                underTemp.next = entry;
                entry.next = temp;
            }
        }
        return value;
    }


    public V putIfAbsent(K key, V value) {
        return this.put(key, value, true);
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public static void main(String[] args) {
        SortedMap<Integer, String> map = new SortedMap<>();
        System.out.println(map.isEmpty());
        map.put(4, "four");
        map.put(1, "One");
        map.put(3, "three");
        map.put(2, "two");
        map.put(6, "Six");
        System.out.println(map.get(5));
        System.out.println(map.putIfAbsent(4, "Five!!!"));
    }
}
