package ru.progwards.java2.lessons.trees;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BinaryTree<K extends Comparable<K>, V> {
    private TreeLeaf<K, V> root;

    public V find(K key) {
        if (root == null)
            return null;
        TreeLeaf found = root.find(key);
        return found.getKey().compareTo(key) == 0 ? (V) found.getValue() : null;
    }

    public void add(TreeLeaf<K, V> leaf) throws TreeException {
        if (root == null) // если корень равен нолю (его попросту нет) - то мы создаём корень
            root = leaf;
        else
            root.find(leaf.getKey()).add(leaf); // а начале ищем наиболее близкий ко входящему листу по сзначению лист, а затем добавляем его - либо в правые потомки, либо в левые
    }

    public void add(K key, V value) throws TreeException {
        add(new TreeLeaf<>(key, value));
    }

    public void delete(K key) throws TreeException {
        internaldDelete(key);
    }

    public TreeLeaf<K, V> internaldDelete(K key) throws TreeException {
        if (root == null) { // если корень нулевой - значит искать нечего. Выбрасываем ошибку.
            throw new TreeException("Key not exists");
        }
        TreeLeaf found = root.find(key);
        int cmp = found.getKey().compareTo(key);// проверяем, что найденное значение не просто максимально похожее на запрашиваемое, а является именно тем самым, которое мы запросили
        if (cmp != 0) { // если всё-таки значение не то - побрасываем ошибку, что такого значение в дереве нет
            throw new TreeException("Key not exists");
        }
        if (found.getParent() == null) { // если родитель у данного листа отсутствует - значит, это корень. С ним мы работает по особенному.
            if (found.getRight() != null) { // вопрос - имеется ли у это корня правый потомок?
                root = found.getRight(); // если да, то теперь именно он становится корнем. С собой он притянет всё своё поддерево
                if (found.getLeft() != null) { // если левый потомок старого не нулевой
                    add(found.getLeft()); // то он у нас остался в подвешенном состоянии. Соответственно, его надо просто добавить в дерево на общих основаниях.
                }
            } else if (found.getLeft() != null) { // в обратном случае - если правый потомок оказался нулевой - мы просто добавляем левый потомок
                root = found.getLeft();
            } else { // если потомков нет вообще - обнуляем корень
                root = null;
            }
        } else
            found.delete(); // если мы имеем дело не с корнем - начинаем перебирать все листы по очереди по принципу "больше (направо) - меньше (налево)". Как будто игра "горячо-холодно".
        if (root != null) {
            root.cutRoot();
        }
        return found;
    }

    public void change(K oldKey, K newKey) throws TreeException { // составной метод. Замена значения = удаление + вставка.
        TreeLeaf<K, V> current = internaldDelete(oldKey); // удаляем старое значение и присваиваем удаляемое в current
        current.setKey(newKey); // меняем ключ
        this.add(current); // вставляемм обратно
    }

    public void process(Consumer<TreeLeaf<K, V>> consumer) { // перебор значений с входящим Consumer
        if (root != null) { // если корень не нулевой - то инициируем проход по всем значениям у данного листа (корня).
            root.process(consumer);
        }
    }

    // Итератор через process
    public Iterator<TreeLeaf<K, V>> getIteratorCons() {
        ArrayList<TreeLeaf<K, V>> list = new ArrayList<>();
        this.process(new Consumer<TreeLeaf<K, V>>() {
            @Override
            public void accept(TreeLeaf<K, V> kvTreeLeaf) {
                list.add(kvTreeLeaf);
            }
        });
        return new Iterator<>() {
            ArrayList<TreeLeaf<K, V>> curr = list;

            @Override
            public boolean hasNext() {
                return list.size() > 0;
            }

            @Override
            public TreeLeaf<K, V> next() {
                return list.remove(0);
            }
        };
    }

    // итератор через лямбду
    public Iterator<TreeLeaf<K, V>> getIteratorRec() {
        return new Iterator<TreeLeaf<K, V>>() {
            ArrayList<TreeLeaf<K, V>> list = prepare();

            @Override
            public boolean hasNext() {
                if (list == null) {
                    this.prepare();
                }
                return !list.isEmpty();
            }

            @Override
            public TreeLeaf<K, V> next() {
                if (list == null) {
                    this.prepare();
                }
                return list.remove(0);
            }

            private ArrayList<TreeLeaf<K, V>> prepare() {
                HashSet<TreeLeaf<K, V>> leaf = new HashSet<>();
                this.add(leaf, root);
                return (ArrayList<TreeLeaf<K, V>>) leaf.stream().sorted(Comparator.comparing(TreeLeaf::getKey)).collect(Collectors.toList());
            }

            private void add(HashSet<TreeLeaf<K, V>> leaf, TreeLeaf curr) {
                leaf.add(curr);
                if (curr.getRight() != null) {
                    this.add(leaf, curr.getRight());
                }
                if (curr.getLeft() != null) {
                    this.add(leaf, curr.getLeft());
                }
            }
        };
    }

    // Итератор через условия
    public Iterator<TreeLeaf<K, V>> getIterator() {
        return new Iterator<TreeLeaf<K, V>>() {
            private TreeLeaf<K, V> curr = root;
            private LinkedList<TreeLeaf<K, V>> list = null;

            public boolean hasNext() {
                if (list == null) {
                    init();
                }
                return list.size() != 0;
            }

            public TreeLeaf<K, V> next() {
                if (list == null) {
                    init();
                }
                return list.pollFirst();
            }


            private void init() {
                boolean end = false;
                list = new LinkedList<>();
                while (!end) {
                    if (checker(curr.getLeft(), true) && checker(curr.getRight(), true)) {
                        curr = curr.getLeft();
                    }
                    if (checker(curr.getLeft(), false) && checker(curr.getRight(), false)) {
                        if (!list.contains(curr)) {
                            list.add(curr);
                        }
                        curr = curr.getParent();
                    }
                    if (checker(curr.getRight(), true) && checker(curr.getLeft(), false)) {
                        list.add(curr); // надо ли?
                        curr = curr.getRight();
                    }
                    if (checker(curr.getLeft(), true) && checker(curr.getRight(), false)) {
                        curr = curr.getLeft();
                    }
                    if (curr.getParent() == null) {
                        end = true;
                    }
                }
            }

            private boolean checker(TreeLeaf leaf, boolean is) {
                return is ? leaf != null && !list.contains(leaf) : leaf == null || list.contains(leaf);
            }
        };
    }
}

