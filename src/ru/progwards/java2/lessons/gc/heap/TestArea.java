package ru.progwards.java2.lessons.gc.heap;

public class TestArea {
    public static void main(String[] args) throws OutOfMemoryException, InvalidPointerException {
        Heap heap = new Heap(15);
        heap.malloc(2);
        heap.malloc(3);
        heap.malloc(4);
        heap.malloc(5);
        heap.free(0);
        heap.free(2);
        heap.compact();
        System.out.println();
    }
}
