package ru.progwards.java2.lessons.old_version.trees;

import java.util.function.Consumer;

public class AvlBinaryTree<K extends Comparable, V> {
    private AvlTreeLeaf<K, V> root;

    public V find(K key) {
        if (root == null)
            return null;
        AvlTreeLeaf found = root.find(key);
        return found.getKey().compareTo(key) == 0 ? (V) found.getValue() : null;
    }

    public void add(AvlTreeLeaf<K, V> leaf) throws TreeException {
        if (root == null) { // если корень равен нолю (его попросту нет) - то мы создаём корень
            root = leaf;
        } else {
            root.find(leaf.getKey()).add(leaf); // а начале ищем наиболее близкий ко входящему листу по сзначению лист, а затем добавляем его - либо в правые потомки, либо в левые
        }
        //this.renovateRoot();
        this.renovateBalance();
    }

    public void add(K key, V value) throws TreeException {
        add(new AvlTreeLeaf<>(key, value));
        this.renovateRoot();
    }

    public void delete(K key) throws TreeException {
        internaldDelete(key);
    }

    public AvlTreeLeaf<K, V> internaldDelete(K key) throws TreeException {
        if (root == null) { // если корень нулевой - значит искать нечего. Выбрасываем ошибку.
            throw new TreeException("Key not exists");
        }
        AvlTreeLeaf found = root.find(key);
        int cmp = found.getKey().compareTo(key);// проверяем, что найденное значение не просто максимально похожее на запрашиваемое, а является именно тем самым, которое мы запросили
        if (cmp != 0) { // если всё-таки значение не то - побрасываем ошибку, что такого значение в дереве нет
            throw new TreeException("Key not exists");
        }
        if (found.getParent() == null) { // если родитель у данного листа отсутствует - значит, это корень. С ним мы работает по особенному.
            if (found.getRight() != null) { // вопрос - имеется ли у это корня правый потомок?
                root = found.getRight(); // если да, то теперь именно он становится корнем. С собой он притянет всё своё поддерево
                root.cutRoot();
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
        if (root!= null) {
            this.renovateRoot();
            this.renovateBalance();
        }
        return found;
    }

    public void change(K oldKey, K newKey) throws TreeException { // составной метод. Замена значения = удаление + вставка.
        AvlTreeLeaf<K, V> current = internaldDelete(oldKey); // удаляем старое значение и присваиваем удаляемое в current
        current.setKey(newKey); // меняем ключ
        this.add(current); // вставляемм обратно
    }

    public void process(Consumer<AvlTreeLeaf<K, V>> consumer) { // перебор значений с входящим Consumer
        if (root != null) { // если корень не нулевой - то инициируем проход по всем значениям у данного листа (корня).
            root.process(consumer);
        }
    }

    private void startRotations() {
        if (root!= null) {
//            Consumer<AvlTreeLeaf<K, V>> consumer = new Consumer<AvlTreeLeaf<K, V>>() {
//                @Override
//                public void accept(AvlTreeLeaf<K, V> kvAvlTreeLeaf) {
//                    if (kvAvlTreeLeaf.getBalance() == 2) {
//                        kvAvlTreeLeaf.balance(kvAvlTreeLeaf, false);
//                    }
//                    if (kvAvlTreeLeaf.getBalance() == -2) {
//                        kvAvlTreeLeaf.balance(kvAvlTreeLeaf, true);
//                    }
//                }
//            };
//            process(consumer);
        }
    }

    private void renovateRoot() {
        while (root.getParent() != null) {
            root = root.getParent();
        }
    }


    private void renovateBalance() {
//        Consumer<AvlTreeLeaf<K, V>> consumer = new Consumer<AvlTreeLeaf<K, V>>() {
//            @Override
//            public void accept(AvlTreeLeaf<K, V> kvAvlTreeLeaf) {
//                kvAvlTreeLeaf.setBalance();
//            }
//        };
//        this.process(consumer);
        root.setBalance(root);
    }
}
