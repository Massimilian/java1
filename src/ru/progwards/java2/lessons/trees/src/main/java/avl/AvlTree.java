package avl;

import java.util.ArrayList;
import java.util.Random;

public class AvlTree<V extends Comparable<V>> {
    private Leaf<V> head;

    // CRUD: Create, Return, Update, Delete
    public V add(V v) {
        if (this.head == null) { // проверка "головы" на ноль
            this.head = new Leaf<>();
        }
        V temp = this.head.add(v); // помещаем элемент в бинарное дерево
        Leaf<V> l = this.get(temp);
        if (l.getParent() != null && l.getParent().getParent() != null) { // проверяем необходимость перебалансировки
            l.rebalance(l.getParent().getParent());
        }
        Leaf newHead = head;
        while (newHead.getParent() != null) { // при необходимости меняем голову
            newHead = newHead.getParent();
        }
        head = newHead;
        return l.getV();
    }

    public Leaf<V> get(V v) {
        Leaf<V> result;
        if (head == null) {
            result = null;
        } else if (head.getV().equals(v)) {
            result = head;
        } else {
            result = head.find(v);
        }
        return result;
    }

    public boolean delete(V v) {
        boolean result = false;
        Leaf forFind = this.get(v);
        if (forFind != null) {
            Leaf parent = forFind.getParent();
            Leaf left = forFind.getLeft();
            Leaf right = forFind.getRight();
            if (left == null && right == null) { // если у элемента нет потомков - то у родителя мы обнуляем ссылку на этот элемент
                if (parent.getLeft().equals(forFind)) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                forFind.rebalance(parent);
            } else if (forFind.getLeft() == null) { // если у элемента нет левых потомков - подтягиваем всё поддерево направо
                if (parent.getLeft().equals(forFind)) {
                    parent.setLeft(right);
                    right.setParent(parent);
                } else {
                    parent.setRight(right);
                    right.setParent(parent);
                }
                parent.rebalance(parent);
            } else { // иначе - делаем шаг налево, доходим до самого правого элемента и делаем переприсвоение
                Leaf temp = forFind.getLeft();
                while (temp.getRight() != null) {
                    temp = temp.getRight();
                }
                Leaf balance = temp.getParent();
                if (parent.getRight().equals(forFind)) {
                    parent.setRight(temp);
                } else {
                    parent.setLeft(temp);
                }
                temp.setRight(right);
                temp.setLeft(left);
                balance.setRightDepth(balance.getRightDepth() - 1);
                balance.setBalance(balance.getRightDepth() - balance.getLeftDepth());
                balance.balanceUp(balance);
            }
            result = true;
        }
        return result;
    }

    public Leaf<V> update(V old, V young) {
        Leaf result = this.get(old);
        if (result != null) {
            this.delete(old);
            this.add(young);
        }
        return result;
    }

    public static void main(String[] args) {
        AvlTree<Integer> ints = new AvlTree<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        Random random = new Random();
        for (int i = 0; i < list.size(); i++) {
            ints.add(list.remove(random.nextInt(list.size())));
        }
        System.out.println();
    }
}

