package ru.progwards.java2.lessons.gc;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;

/**
 * Special heap keeper without hole-keeper system
 */
public class Heap3 implements FatherHeap {
    int position = 0;
    byte[] bytes;

    public Heap3(int maxHeapSize) {
        bytes = new byte[maxHeapSize];
        Arrays.fill(bytes, (byte) 0); // 0 - free, 1 - busy, 2 - start.

    }

    @Override
    public int malloc(int size) throws InvalidPointerException, OutOfMemoryException {
        int result = position;
        if (size > 0) {
            if (position + size > bytes.length) {
                compact();
                if (position + size > bytes.length) {
                    throw new OutOfMemoryException();
                }
            }
            if (bytes[position] == 0) {
                bytes[position++] = 2;
                for (int i = 1; i < size; i++) {
                    bytes[position++] = 1;
                }
            } else {
                throw new InvalidPointerException();
            }
        }
        return result;
    }

    @Override
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] == 2) {
            bytes[ptr++] = 0;
            while (bytes.length != ptr && bytes[ptr] == 1) {
                bytes[ptr++] = 0;
            }
        } else {
            throw new InvalidPointerException();
        }
    }

    @Override
    public void clear() {
        bytes = new byte[bytes.length];
        position = 0;
    }

    private void compact() {
        byte[] newBytes = new byte[bytes.length];
        int newPos = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 2 || bytes[i] == 1) {
                newBytes[newPos++] = bytes[i];
            }
        }
        bytes = newBytes;
        position = newPos;
    }

    public static void main(String[] args) throws InvalidPointerException, OutOfMemoryException {
        Heap3 h = new Heap3(10);
        h.malloc(1);
        h.malloc(2);
        h.malloc(3);
        h.malloc(4);
        h.free(0);
        h.free(6);
        h.malloc(5);
        System.out.println();
    }
}
