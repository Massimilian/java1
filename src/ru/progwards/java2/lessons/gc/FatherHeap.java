package ru.progwards.java2.lessons.gc;

/**
 * Interface of different types of heap
 */
public interface FatherHeap {
    int malloc(int size) throws OutOfMemoryException, InvalidPointerException;

    void free(int ptr) throws InvalidPointerException;

    void clear();
}
