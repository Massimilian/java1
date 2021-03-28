package ru.progwards.java2.lessons.basetypes;

public class Node<T> {
    private Node<T> prev;
    private Node<T> post;
    T t;

    public Node(T t) {
        this.t = t;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getPost() {
        return post;
    }

    public void setPost(Node<T> post) {
        this.post = post;
    }

    public T getT() {
        return t;
    }

    @Override
    public boolean equals(Object obj) {
        return this.t.equals(((Node) obj).getT());
    }
}
