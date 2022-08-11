package discret;

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

    /**
     * Метод передаёт key и по этому ключу получает value (значение).
     * @param key - ключ для поиска соответствующего сочетания "ключ-значение".
     * @return значение (value), соответствующее данному ключу
     */
    public V get(K key) {
        V result = null;
        SortedMap.Entry<K, V> temp = first; // создаём отдельную ссылку на Entry, на который также ссылается first
        while (temp != null && key.compareTo(temp.key) > 0) {
            // temp может оказаться нулевым в двух случаях - если SortedMap пустой и если мы дошли до конца списка;
            // Если сравнение дало результат меньше ноля, значит - key меньше, чем значение key из текущего объекта
            temp = temp.next; // если значение не нулевое и запрашиваемый key меньше, чем key объекта - берём следующий Entry
        }
        if (temp != null && key.compareTo(temp.key) == 0) {
            result = temp.value; // если объект не нулевой и key совпали - мы нашли объект
        }
        return result;
    }

    /**
     * Метод пытается положить значение по ключу; если значение по ключу уже лежит - то оно заменяется на новое
     * @param key - ключ, по которому мы кладём значение
     * @param value - значение, которое мы кладём
     * @return - значение, которое мы получаем по ключу key
     */
    public V put (K key, V value) {
        return put(key, value, false);
    }

    /**
     * При добавлении в сортированную очередь у нас могут быть три варианта:
     * 1. Очередь пуста. Тогда мы просто приравниванием first очереди к новому Entry.
     * 2. Key меньше минимального (первого) key в очереди. Тогда мы кладём новый Entry на место first, а ссылку на first (как next) кладём в новый объект.
     * 3. Key больше минимального (первого) key в очереди. Тогда мы пошагово ищем место для нового Entry (если isAbsent == true),
     * либо пытаемся заменить существующий объект (если isAbsent == false), пытаясь найти соответствующее место между двумя Entry.
     * @param key - ключ нового Entry
     * @param value - значение нового Entry
     * @param isAbsent - является ли вызов этого метода из метода isAbsent
     * @return value по соответствующему key
     */
    public V put(K key, V value, boolean isAbsent) {
        if (isEmpty()) { // вариант №1
            this.first = new SortedMap.Entry<>(key, value);
        } else if (first.key.compareTo(key) > 0) { // вариант №2
            SortedMap.Entry<K, V> entry = new SortedMap.Entry<>(key, value);
            entry.next = this.first;
            this.first = entry;
        } else { // вариант №3
            SortedMap.Entry<K, V> temp = first;
            SortedMap.Entry<K, V> underTemp = null;
            while(temp != null && temp.key.compareTo(key) < 0) {
                underTemp = temp;
                temp = temp.next;
            }
            if (temp == null) { // key оказался самым больше, чем самый большой в данной SortedMap
                temp = new SortedMap.Entry<>(key, value);
                underTemp.next = temp;
            }
            if (temp.key.compareTo(key) == 0) { // отработка на случай, если такой ключ уже существует
                if (!isAbsent) {
                    temp.value = value;
                } else {
                    value = temp.value;
                }
            } else { // вариант, что такого ключа нет и мы нашли место для нового Entry; осталось только переписать ссылки на следующие Entry
                SortedMap.Entry<K, V> entry = new SortedMap.Entry<>(key, value);
                underTemp.next = entry;
                entry.next = temp;
            }
        }
        return value;
    }

    /**
     * Метод пытается добавить значение; если значение по данному ключу существует, то ничего не добавляется
     * @param key - ключ, по которому мы пытаемся добавить значение
     * @param value - значение для возможного добавления
     * @return - значение, которое SortedMap возвращает по данному ключу.
     */
    public V putIfAbsent(K key, V value) {
        return put(key, value, true);
    }

    /**
     * Проверка SortedMap на отсутствие значений
     * @return - значения отсутствуют
     */
    public boolean isEmpty() {
        return this.first == null;
    }

    /**
     * Метод для тестирования работы программы
     * @param args
     */
    public static void main(String[] args) {
        SortedMap<Integer, String> map = new SortedMap<>();
        System.out.println(map.isEmpty());
        map.put(4, "four");
        map.put(1, "One");
        map.put(3, "three");
        map.put(2, "two");
        map.put(6, "Six");
        System.out.println(map.get(5));
        System.out.println(map.get(2));
        System.out.println(map.putIfAbsent(4, "Five!!!"));
    }
}
