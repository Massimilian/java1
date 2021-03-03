package ru.progwards.java2.lessons.basetypes;

public class Node<T> {
    T value;
    Node preview;
    Node next;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node getPreview() {
        return preview;
    }

    public void setPreview(Node preview) {
        this.preview = preview;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
