package ru.progwards.java2.lessons.basetypes.bidirlist;

import java.util.Iterator;

public class BiDirList<T> {
    private Node<T> main;
    private Node<T> tail;
    private int count = 0;

    public BiDirList() {
    }

    private BiDirList(Node<T> main, Node<T> tail, int count) {
        this.main = main;
        this.tail = tail;
        this.count = count;
    }

    public void addLast(T item) {  // добавить в конец списка
        Node<T> node = new Node(item);
        if (main == null) {
            this.addIfMainIsNull(node);
        } else {
            tail.setNext(node);
            node.setPreview(tail);
            tail = node;
            count++;
        }
    }

    public void addFirst(T item) {// добавить в начало списка
        Node<T> node = new Node(item);
        if (main == null) {
            this.addIfMainIsNull(node);
        } else {
            node.setNext(main);
            main.setPreview(node);
            main = node;
            count++;
        }
    }

    private void addIfMainIsNull(Node<T> node) {
        this.main = node;
        this.tail = node;
        this.count++;
    }

    public void remove(T item) { // удалить
        if (count == 0) {
            return;
        } else {
            Node<T> temp = main;
            do {
                if (temp.getValue() != null && temp.getValue().equals(item)) {
                    this.deleteNode(temp);
                    count--;
                    break;
                }
                temp = temp.getNext();
            } while (temp != null);
        }
    }

    private void deleteNode(Node temp) {
        boolean changeRoots = false;
        if (temp.equals(tail)) {
            tail = tail.getPreview();
            changeRoots = true;
        } else if (temp.equals(main)) {
            main = main.getNext();
            changeRoots = true;
        }
        if (temp.getPreview() != null && !changeRoots) {
            temp.getPreview().setNext(temp.getNext());
        }
        if (temp.getNext() != null && !changeRoots) {
            temp.getNext().setPreview(temp.getPreview());
        }
        temp.setValue(null);
    }

    public T at(int i) {
        T t = null;
        if (i < size() && i >= 0) {
            Node<T> node = main;
            int count = 0;
            while (count != i) {
                node = node.getNext();
                count++;
            }
            t = node.getValue();
        }
        return t;
    }

    public int size() { // получить количество элементов
        return this.count;
    }

    public static <T> BiDirList<T> from(T[] array) { // конструктор из массива
        BiDirList<T> fromArr = null;
        if (array.length > 0) {
            Node<T> main = new Node(array[0]);
            Node<T> tail = new Node(array[0]);
            int count = 1;
            for (int i = 1; i < array.length; i++) {
                Node<T> node = new Node(array[i]);
                if (main.getValue().equals(tail.getValue())) {
                    main.setNext(node);
                    node.setPreview(main);
                    tail = node;
                } else {
                    tail.setNext(node);
                    node.setPreview(tail);
                    tail = node;
                }
                count++;
            }
            fromArr = new BiDirList(main, tail, count);
        }
        return fromArr;
    }

    public static <T> BiDirList<T> of(T... array) { // конструктор из массива
        return from(array);
    }

    public static void toArray(Object[] array) { // скопировать в массив
        // не понял назначение этого метода...
    }

    public Iterator<T> getIterator() { // получить итератор
        Iterator it = new Iterator<T>() {
            private Node<T> itMain = main;
            private Node<T> itTail = tail;

            @Override
            public boolean hasNext() {
                return itMain != null;
            }

            @Override
            public T next() {
                Node<T> temp = itMain;
                itMain = itMain.getNext();
                return temp.getValue();
            }

            @Override
            public void remove() {
            }
        };
        return it;
    }
}
