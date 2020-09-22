package ru.progwards.java2.lessons.gc_val;

public class Heap2 {
    byte[] bytes;
    int allocated;

    Heap2(int maxHeapSize) {
        bytes = new byte[maxHeapSize];
        allocated = 0;
    }

    public int malloc(int size) {
        int ptr = allocated;
        allocated += size;
        return ptr;
    }

    public void free(int ptr) {
    }
}
