package ru.progwards.java2.lessons.gc_konst;

public class MemoryBlock {
    public int ptr;
    public int size;

    public MemoryBlock(int ptr, int size) {
        this.ptr = ptr;
        this.size = size;
    }
}

