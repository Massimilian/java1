package ru.progwards.java2.lessons.old_version.trees;

import java.util.function.Consumer;

public class AvlTreeLeaf<K extends Comparable, V> {
    private K key;
    private V value;
    private AvlTreeLeaf parent;
    private AvlTreeLeaf left;
    private AvlTreeLeaf right;
    private int balance = 0;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public int getBalance() {
        return balance;
    }

    public AvlTreeLeaf getParent() {
        return parent;
    }

    public AvlTreeLeaf getLeft() {
        return left;
    }

    public AvlTreeLeaf getRight() {
        return right;
    }

    public void setParent(AvlTreeLeaf parent) {
        this.parent = parent;
    }

    public void setLeft(AvlTreeLeaf left) {
        this.left = left;
    }

    public void setRight(AvlTreeLeaf right) {
        this.right = right;
    }

    public AvlTreeLeaf(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public AvlTreeLeaf<K, V> find(K key) {
        int cmp = key.compareTo(this.key); // сравниваем входящий ключ с тем, который представлен в данном листе
        if (cmp > 0) { // если входящий ключ меньше
            if (right != null) {// то проверяем наличие правого потомка
                return right.find(key); // и, если он есть - рекурсивно обращаемся к правому потомку
            } else {
                return this; // иначе - возвращаем родителя (за неимением правого потомка), как наиболее близкое значение
            }
        }
        if (cmp < 0) {// если входящий ключ больше
            if (left != null) {//значит, мы имеем дело с левой стороной. Проверяем на ноль
                return left.find(key); // если там есть значение - рекурсивно обращаемся к потомку слева
            } else {
                return this; // иначе - возвращаем данное значение, как наиболее близкое
            }
        }
        return this; // если запрашиваемый ключ равен представленному в данном объекте, значит - мы нашли совпадающий ключ либо наиболее близкий
    }

    void add(AvlTreeLeaf<K, V> leaf) throws TreeException {
        int cmp = leaf.key.compareTo(key); // проверяем на равенство значением ключа и входящее значение ключа
        if (cmp == 0) { // если они равны - то пробрасываем ошибку
            throw new TreeException("Key not exists");
        }
        if (cmp > 0) {
            right = leaf; // если входящее значение больше - засовываем входящий лист в правый потомок
            leaf.parent = this; // и делаем значение данного листа родителем для входящего значения
        } else {
            left = leaf; // если входящее значение меньше - засовываем входящий лист в левый потомок
            leaf.parent = this; // делаем значение данного листа родителем для входящего значения
        }
        checkBalance(leaf);
    }

    void delete() throws TreeException {
        checkBalanceForDel(this.parent, this, 0);
        if (parent.right == this) { // проверка на то, что значение является правым потомком родителя
            parent.right = right; // родителя присваиваем значение правого потомка
            if (right != null) {// а если он не нулевой (если нулевой - просто сохранится ссылка на null)
                right.parent = parent; // тогда указываем ему на нового родителя
            }
            if (left != null) {// также надо позаботиться о левом потомке. Если он не нулевой
                parent.find(left.key).add(left); // просто добавляем его в таблицу
            }
        } else { // если же значение не является правым потомком - оно является левым потомком
            parent.left = this.left; // родителю присваиваем значение левого потомка
            if (left != null) {// если значение не нулевое
                left.parent = parent; // то  указываем левому потомку на нового родителя
            }
            if (right != null) { // если правый потомок не нулевой
                parent.find(right.key).add(right); // то присоединяем его к дереву на общих основаниях
            }
        }
    }

    private void checkBalanceForDel(AvlTreeLeaf parent, AvlTreeLeaf child, int value) {
        if (parent != null) {
            boolean cont = false;
            if (parent.getRight() == child) {
                value--;
                cont = parent.getLeft() == null;
            }
            if (parent.getLeft() == child) {
                value++;
                cont = parent.getRight() == null;
            }
            parent.balance += value;
            if (cont) {
                checkBalanceForDel(parent.getParent(), parent, value);
            }
        }
    }

    public String toString() {
        return "(" + key + "," + value + ")";
    }

    public void process(Consumer<AvlTreeLeaf<K, V>> consumer) {
        if (left != null) {// работает рекурсия. Перебор надо сделать слева направо. Соответственно, обращаемся к самому левому корню
            left.process(consumer); // если левое значение есть - рекурсивно обращаемся к нему
        }
        consumer.accept(this); // если левое значение отсутствует - значит, проделываем работу с данным значением
        if (right != null) // если правое значение не равно нолю
            right.process(consumer); // тогда переходим туда. Но  - важно! - только после левого. Именно благодаря этому порядку значения будут выводиться по возрастающей.
    }

    private void checkBalance(AvlTreeLeaf temp) {
        boolean hasNotAnotherValue = true;
        if (temp.getParent() != null) {
            if (temp.getParent().getLeft() != null && temp.getParent().getLeft().equals(temp)) {
                temp = temp.getParent();
                temp.balance--;
                hasNotAnotherValue = temp.getRight() == null;
            } else {
                temp = temp.getParent();
                temp.balance++;
                hasNotAnotherValue = temp.getLeft() == null;
            }
        }
        if (hasNotAnotherValue) {
            while (temp.getParent() != null) {
                if (temp.getParent().getLeft() != null && temp.getParent().getLeft().equals(temp)) {
                    temp = temp.getParent();
                    temp.balance--;
                    if (temp.balance == -2) {
                        balance(temp, true);
                        break;
                    }
                } else {
                    temp = temp.getParent();
                    temp.balance++;
                    if (temp.balance == 2) {
                        balance(temp, false);
                        break;
                    }
                }
            }
        }
    }

    public void balance(AvlTreeLeaf temp, boolean leftBigger) {
        AvlTreeLeaf leaf;
        if (leftBigger) { // перевешивает левая сторона
            leaf = temp.getLeft();
            //if (findTreeHeight(leaf.getRight()) < findTreeHeight(leaf.getLeft())) {
            if (leaf.balance <= 0) {
                smallRightRotation(temp, leaf, leaf.getRight());
            } else {
                smallLeftRotation(leaf, leaf.getRight(), leaf.getRight().getLeft());
                leaf = leaf.getParent();
                smallRightRotation(leaf.getParent(), leaf, leaf.getRight());
            }
        } else { // перевешивает правая сторона
            leaf = temp.getRight();
            if (leaf.balance >= 0) {
                smallLeftRotation(temp, leaf, leaf.getLeft());
            } else {
                smallRightRotation(leaf, leaf.getLeft(), leaf.getLeft().getRight());
                leaf = leaf.getParent();
                smallLeftRotation(leaf.getParent(), leaf, leaf.getLeft());
            }
        }
//        checkBalance(leaf); // надо ли?
    }

    private void smallRightRotation(AvlTreeLeaf top, AvlTreeLeaf around, AvlTreeLeaf jumper) {
        AvlTreeLeaf mainParent = top.getParent() != null ? top.getParent() : null;
        around.setRight(top);
        around.setParent(mainParent);
        around.balance++; // new
        if (mainParent != null) {
//            mainParent.setRight(around);
            if (mainParent.getRight() != null && mainParent.getRight() == top) {
                mainParent.setRight(around);
            } else {
                mainParent.setLeft(around);
//                mainParent.balance = mainParent.balance + 1;
            }
        }
        top.setParent(around);
        top.setLeft(jumper);
        top.balance = top.balance + 2; // new
        if (jumper != null) {
            jumper.setParent(top);
        }
    }

    private void smallLeftRotation(AvlTreeLeaf top, AvlTreeLeaf around, AvlTreeLeaf jumper) {
        AvlTreeLeaf mainParent = top.getParent() != null ? top.getParent() : null;
        around.setLeft(top); // new
        around.setParent(mainParent);
        around.balance--; // new
        if (mainParent != null) {
//            mainParent.setLeft(around);
            if (mainParent.getRight() != null && mainParent.getRight() == top) {
                mainParent.setRight(around);
            } else {
                mainParent.setLeft(around);
            }
//            mainParent.balance = mainParent.balance - 1; // new
        }
        top.setParent(around);
        top.setRight(jumper);
        top.balance = top.balance - 2; // new
        if (jumper != null) {
            jumper.setParent(top);
        }
    }

    private int findTreeHeight(AvlTreeLeaf avl) {
        int result = 0;
        if (avl != null) {
            int depth = 1;
            while (avl.getLeft() != null) {
                avl = avl.getLeft();
                depth++;
            }
            result = depth;
            do {
                if (avl.getLeft() != null) {
                    avl = avl.getLeft();
                    depth++;
                } else if (avl.getRight() != null) {
                    avl = avl.getRight();
                    depth++;
                } else {
                    while (avl.getParent() != null && avl.getRight() == null) {
                        avl = avl.getParent();
                        depth--;
                    }
                }
                result = Math.max(depth, result);
            } while (depth > 1);
        }
        return result;
    }

//    public void setBalance() {
//        this.balance = this.findTreeHeight(this.getRight()) - this.findTreeHeight(this.getLeft());
//    }

    public int setBalance(AvlTreeLeaf<K, V> leaf) {
//        int left = leaf.getLeft() == null? 0 : this.setBalance(leaf.getLeft());
//        int right = leaf.getRight() == null? 0 : this.setBalance(leaf.getRight());
//        return right - left;
        return leaf.balance;
    }

    public void cutRoot() {
        this.parent = null;
    }
}