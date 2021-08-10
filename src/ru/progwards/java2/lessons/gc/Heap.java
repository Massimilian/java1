package ru.progwards.java2.lessons.gc;

import java.util.*;

/**
 * Special heap keeper with auto-defrag (real-time-join-hole) system
 */
public class Heap implements FatherHeap {
    byte[] bytes;
    NavigableMap<Integer, Integer> holes = new TreeMap<>();
    int count = 0;

    Heap(int maxHeapSize) {
        this.bytes = new byte[maxHeapSize];
    }

    @Override
    public int malloc(int size) throws OutOfMemoryException {
        boolean newPosition = false;
        int position;
        if ((position = findBiggerHole(size)) == -1) {
            if (hasNoMorePlace(size)) {
                compactAndCheck(size);
            }
            position = this.count;
            newPosition = true;
        }
        placeIt(position, size, newPosition);
        return position;
    }

    /**
     * Method for find the current hole.
     *
     * @param size is a position of this hole
     * @return
     */
    private int findBiggerHole(int size) {
        int result = -1;
        for (Map.Entry<Integer, Integer> entry : holes.entrySet()) {
            if (entry.getValue() <= size) {
                result = entry.getKey();
                entry.setValue(entry.getValue() - size);
                if (entry.getValue() != 0) {
                    holes.put(entry.getKey() + size, entry.getValue() - size);
                }
                holes.remove(entry.getKey());
                break;
            }
        }
        return result;

    }

    @Override
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] == 2) {
            int size = deleteBlock(ptr);
            joinHoles(ptr, size);
        } else {
            throw new InvalidPointerException();
        }
    }

    @Override
    public void clear() {
        this.bytes = new byte[bytes.length];
        this.holes = new TreeMap<>();
        this.count = 0;
    }

    /**
     * Method for compactisation all memory massiv (bytes) and check the possibility of checking a new object with current size
     *
     * @param size the size of an object to put
     * @throws OutOfMemoryException
     */
    private void compactAndCheck(int size) throws OutOfMemoryException {
        byte[] newBytes = new byte[this.bytes.length];
        int count = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (this.bytes[i] != 0) {
                newBytes[count++] = bytes[i];
            }
        }
        bytes = newBytes;
        this.count = count;
        this.holes = new TreeMap<>();
        if (hasNoMorePlace(size)) {
            throw new OutOfMemoryException();
        }
    }

    /**
     * method for check isn't free place for a new object
     *
     * @param size is a size of object for malloc
     * @return cannot put the object
     */
    private boolean hasNoMorePlace(int size) {
        return this.bytes.length < this.count + size;
    }

    /**
     * Method for looking the holes near new hole
     *
     * @param ptr  position of a new hole
     * @param size size of a new hole
     */
    private void joinHoles(int ptr, int size) {
        int start = -1;
        int end = -1;
        if (ptr != 0) {
            Map.Entry<Integer, Integer> entry = holes.lowerEntry(ptr);
            if (entry != null && entry.getKey() + entry.getValue() == ptr) {
                start = entry.getKey();
                size = ptr - start;
            }
        }
        if (ptr != size - 1) {
            Map.Entry<Integer, Integer> entry = holes.higherEntry(ptr);
            if (entry != null && entry.getKey() == ptr + size) {
                end = size + (start != -1 ? ptr - start : 0) + entry.getValue();
                holes.remove(entry.getKey());
            }
        }
        holes.put(start != -1 ? start : ptr, end != -1 ? end : size);
    }

    /**
     * method for exact delete of an object from a memory array (bytes)
     *
     * @param ptr is the start position of an object for delete
     * @return size of deleted object
     */
    private int deleteBlock(int ptr) {
        int count = 1;
        bytes[ptr] = 0;
        while (ptr < bytes.length - 1 && bytes[++ptr] == 1) {
            bytes[ptr] = 0;
            count++;
        }
        return count;
    }

    /**
     * method for exact put on object from a memory array (bytes)
     *
     * @param point       position of an object
     * @param size        size of an object
     * @param newPosition is new position required
     */
    private void placeIt(int point, int size, boolean newPosition) {
        if (size-- > 0) {
            bytes[point] = 2;
        }
        while (size-- > 0) {
            bytes[++point] = 1;
        }
        this.count = newPosition ? ++point : this.count;
    }
}
