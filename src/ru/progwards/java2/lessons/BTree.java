package ru.progwards.java2.lessons;

public class BTree<K extends Comparable<K>, V> {

    private Page<K, V> root; // корневая страница

    public V find(K key) {
        Page cur = root; // начинаем поиск с корневой страницы
        while(cur != null) {  // если корень не равен нолю
            int i = cur.findKey2(key); // то начинаем поиск ключа тут и переходим вглубь страницы
            if (i < cur.keys.length && key.compareTo((K)cur.keys[i]) == 0) // если мы нашли соответствующий ключ...
                return (V)cur.values[i]; // ...то заканчиваем поиск и возвращаем соответствующее значение
            cur = cur.children[i]; // если нет - берём страницу, которая соответствует потомку именно этого ключа и продолжаем поиск
        }
        return null;
    }

    public void add(K key, V value) { // алгоритм вставки
        if (root == null || root.isFull()) { // если корень нулевой или корень полностью заполнен
            Page<K, V> newroot = new Page<>(); // то создаём новую страницу корня (не забываем - здесь мы растём от листьев к корню, а не от корня к листьяи)
            if (root != null) { // если root переполнен
                root.parent = newroot; // то делаем его родителем новый root
                root.splitPage(); // производим функцию разбиения страниц
            }
            root = newroot;
        }
        // search to insert
        Page cur = root;
        int i = -1;
        while (cur != null) {
            if (cur.isFull())
                cur.splitPage();
            i = cur.findKey2(key);
            if (i < cur.keys.length && key.compareTo((K) cur.keys[i]) == 0)
                cur.setItem(key, value);
            cur = cur.children[i];
        }
        cur.addItem(i, key, value, null);
    }

}
