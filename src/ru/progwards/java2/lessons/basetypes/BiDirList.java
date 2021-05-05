package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;

/**
 * Class to keep values in doubly linked list
 * @param <T> is a type of values
 */
public class BiDirList<T> implements Iterable<T> {
    private Node<T> base;
    private Node<T> tail;
    private int size = 0;

    public static <T> BiDirList from(T[] array) {
        BiDirList result = new BiDirList();
        for (T t : array) {
            result.addLast(t);
        }
        return result;
    }

    public static <T> BiDirList<T> of(T... array) {
        return from(array);
    }


    /**
     * Add value in the tail of list
     * @param item is a value
     */
    public void addLast(T item) {
        Node<T> node = new Node<>(item);
        if (!addIfNull(node)) {
            if (this.tail == null) {
                addNewLast(base, node);
            } else {
                addNewLast(tail, node);
            }
        }
        size++;
    }

    /**
     * Method for adding in the list
     * @param prev is a preview value
     * @param post is a postview value
     */
    private void addNewLast(Node prev, Node post) {
        prev.setPost(post);
        post.setPrev(prev);
        this.tail = post;
    }

    /**
     * Add value in the head of list
     * @param item is a value
     */
    public void addFirst(T item) {
        Node<T> node = new Node<>(item);
        if (!addIfNull(node)) {
            this.base.setPrev(node);
            node.setPost(this.base);
            this.base = node;
        }
        size++;
    }

    /**
     * Method for remove value from list
     * @param item is a value for remove
     */
    public void remove(T item) {
        boolean notExists = false;
        Node<T> pointer = this.base;
        while (!notExists && !pointer.getT().equals(item)) {
            pointer = pointer.getPost();
            notExists = pointer == null;
        }
        if (!notExists) {
            if (this.base.getT().equals(item)) {
                if (base.getPost() != null) {
                    base.getPost().setPrev(null);
                }
                base = base.getPost();
            } else if (this.tail.getT().equals(item)) {
                tail.getPrev().setPost(null);
                if (size == 1) {
                    tail = null;
                } else {
                    tail = tail.getPrev();
                }
            } else {
                pointer.getPost().setPrev(pointer.getPrev());
                pointer.getPrev().setPost(pointer.getPost());
            }
            size--;
        } else {
            System.out.println("Impossible value for delete.");
        }
    }

    /**
     * Method for get value by its position
     * @param i is a number of position
     * @return value
     */
    public T at(int i) {
        Node<T> pointer = null;
        if (i <= this.size) {
            pointer = this.base;
            for (int j = 0; j < i; j++) {
                pointer = pointer.getPost();
            }
        }
        return pointer != null ? pointer.getT() : null;
    }

    /**
     * Return a current size of list
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * put array into the list
     * @param array is a array for put
     */
    public void toArray(T[] array) {
        for (T t : array) {
            this.addLast(t);
        }
    }

    /**
     * Put the list into array
     * @return array from list
     */
    public T[] toArray() {
        T[] result = (T[]) new Object[this.size];
        Iterator<T> iterator = this.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            result[count++] = iterator.next();
        }
        return result;
    }

    /**
     * Method for prepare an iterator
     * @return prepared iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> current = base;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T t = current.getT();
                current = current.getPost();
                return t;
            }
        };
    }

    /**
     * method for the first put into the list
     * @param node for put
     * @return is put
     */
    private boolean addIfNull(Node<T> node) {
        boolean result = false;
        if (this.base == null) {
            this.base = node;
            result = true;
        }
        return result;
    }

    public static void main(String[] args) {
        BiDirList<Integer> bdl = new BiDirList<>();
        bdl.addLast(0);
        bdl.addLast(1);
        bdl.addLast(2);
        bdl.addFirst(-1);
        bdl.addFirst(-2);
        assert bdl.size() == 5;
        bdl.remove(0);
        bdl.remove(3);
        bdl.remove(-3);
        assert bdl.toArray().length == 4;
        bdl.remove(2);
        bdl.remove(-2);
        assert bdl.toArray().length == 2;
    }
}
