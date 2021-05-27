package ru.progwards.java2.lessons.gc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Special heap keeper with auto-defrag (real-time-join-hole) system
 */
public class Heap implements FatherHeap {
    byte[] bytes;
    HashMap<Integer, Integer> holes = new HashMap<>();
    int count = 0;

    Heap(int maxHeapSize) {
        this.bytes = new byte[maxHeapSize];
        Arrays.fill(bytes, (byte) 0);
    }

    /**
     * Add new object into the memory array (bytes)
     *
     * @param size is a number of positions into the array
     * @return start position of a new object into the memory
     * @throws OutOfMemoryException
     */
    public int malloc(int size) throws OutOfMemoryException {
        boolean newPosition = false;
        int position;
        if (holes.containsValue(size)) {
            position = findKey(holes, size);
            holes.remove(position);
        } else {
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
     * delete object from the memory array (bytes) and join (if necessary) the hole
     *
     * @param ptr position of an object for delete
     * @throws InvalidPointerException
     */
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] == 2) {
            int size = deleteBlock(ptr);
            joinHoles(ptr, size);
        } else {
            throw new InvalidPointerException();
        }
    }

    /**
     * Method to clear and restart all memory system
     */
    public void clear() {
        this.bytes = new byte[bytes.length];
        this.holes.clear();
        this.count = 0;
        Arrays.fill(bytes, (byte) 0);
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
        this.holes = new HashMap<>();
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
     * Find the position of a hole into the memory array (bytes)
     *
     * @param holes all found holes
     * @param size  requied size of hole
     * @return the position of the hole
     */
    private int findKey(HashMap<Integer, Integer> holes, int size) {
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : holes.entrySet()) {
            if (entry.getValue() == size) {
                result = entry.getKey();
                break;
            }
        }
        return result;
    }

    /**
     * Method for looking the holes near new hole
     *
     * @param ptr  position of a new hole
     * @param size size of a new hole
     */
    private void joinHoles(int ptr, int size) {
        while (ptr != 0 && bytes[ptr - 1] == 0) {
            size++;
            ptr--;
        }
        if (ptr != size - 1 && bytes[ptr + size - 1] == 0) {
            holes.remove(ptr + size);
        }
        while (ptr != size - 1 && bytes.length < ptr + size - 1 && bytes[ptr + size - 1] == 0) {
            size++;
        }
        if (holes.containsKey(ptr)) {
            holes.replace(ptr, size);
        } else {
            holes.put(ptr, size);
        }
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


    public static void main(String[] args) throws HeapException {
        Heap heap = new Heap(24);
        heap.malloc(3);
        heap.malloc(3);
        heap.malloc(3);
        heap.malloc(3);
        heap.malloc(3);
        heap.malloc(3);
        heap.free(3);
        heap.free(9);
        heap.free(6);
        heap.malloc(3);
        heap.malloc(9);
        heap.free(3);
        heap.malloc(6);
        heap.malloc(6);
        System.out.println();
    }
}
