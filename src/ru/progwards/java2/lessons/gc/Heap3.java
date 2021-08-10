package ru.progwards.java2.lessons.gc;

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


    @Override
    public void getBytes(int ptr, byte[] bytes) {
        System.arraycopy(this.bytes, ptr, bytes, 0, bytes.length);
    }

    @Override
    public void setBytes(int ptr, byte[] bytes) {
        System.arraycopy(bytes, 0, this.bytes, ptr, bytes.length);
    }
}
