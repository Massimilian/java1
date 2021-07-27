package ru.progwards.java2.lessons.gc;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Special heap keeper with the system of special object (holes)
 */
public class Heap2 implements FatherHeap {
    byte[] bytes;
    int count = 0;
    ArrayList<Hole> holes = new ArrayList<>();
    int renovationHolesTime = 0;

    Heap2(int maxHeapSize) {
        this.bytes = new byte[maxHeapSize];
        Arrays.fill(bytes, (byte) 0);
    }

    /**
     * Method to add new position into the memory array
     *
     * @param size size of a new object
     * @return position of s new object
     * @throws OutOfMemoryException
     */
    public int malloc(int size) throws OutOfMemoryException {
        if (size + count - 1 >= bytes.length) {
            compact();
        }
        int start = count;
        tryAdd(size);
        return start;
    }

    /**
     * Method for compact heap with holes and put all holes at the end of the memory array (bytes)
     */
    private void compact() {
        byte[] newBytes = new byte[bytes.length];
        Arrays.fill(newBytes, (byte) 0);
        int newCount = 0;
        for (int i = 0; i < count; i++) {
            if (bytes[i] != 0) {
                newBytes[newCount++] = bytes[i];
            }
        }
        bytes = newBytes;
        count = newCount;
        renovationHolesTime = 0;
        holes.clear();
    }

    /**
     * Method to try to add a new object into the memory array (bytes)
     *
     * @param size size of a new object
     * @throws OutOfMemoryException
     */
    private void tryAdd(int size) throws OutOfMemoryException {
        if (size + count <= bytes.length) {
            int position = this.findCurrentHole(size);
            if (position == count) {
                this.put(count, size);
                count += size;
            } else {
                this.put(position, size);
                checkHoles(position, size);
            }
        } else {
            throw new OutOfMemoryException();
        }
    }

    /**
     * Method for delete memory block
     *
     * @param ptr position of a block for delete
     * @throws InvalidPointerException
     */
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] == 2) {
            deleteBlock(ptr);
        } else {
            throw new InvalidPointerException();
        }
        // перепроованы различные значения (от 1 до 1000). 10 оказалось оптимальным по показателям.
        if (++this.renovationHolesTime > bytes.length / 10) {
            renovationHolesTime = 0;
            defrag();
        }
    }

    /**
     * Method for whole restart
     */
    public void clear() {
        this.bytes = new byte[bytes.length];
        this.holes.clear();
        this.count = 0;
        this.renovationHolesTime = 0;
    }

    /**
     * Method for join all neightbour holes
     */
    public void defrag() {
        holes.clear();
        Hole hole = null;
        for (int i = 0; i < this.count; i++) {
            if (bytes[i] == 0) {
                if (hole == null) {
                    hole = new Hole(1, i);
                } else {
                    hole.incrSize();
                }
            } else {
                if (hole != null) {
                    holes.add(hole);
                    hole = null;
                }
            }
        }
    }

    /**
     * Method for exact delete pf a memory block
     *
     * @param ptr position of a block
     */
    private void deleteBlock(int ptr) {
        int newHolePos = ptr;
        int newHoleSize = 1;
        bytes[ptr] = 0;
        while (ptr < bytes.length - 1 && bytes[++ptr] == 1 && ptr < count) {
            bytes[ptr] = 0;
            newHoleSize++;
        }
        holes.add(new Hole(newHoleSize, newHolePos));
    }

    /**
     * Method for change the size of a hole
     *
     * @param position is hole place
     * @param size     size for cut
     */
    private void checkHoles(int position, int size) {
        Hole hole = holes.get(holes.indexOf(new Hole(position)));
        hole.setSize(hole.getSize() - size);
        if (hole.getSize() == 0) {
            holes.remove(hole);
        }
    }

    /**
     * Method for put a new block into the memory array (bytes)
     *
     * @param position position for add
     * @param size     size of a block
     */
    private void put(int position, int size) {
        bytes[position] = 2;
        for (int i = 1; i < size; i++) {
            bytes[i + position] = 1;
        }
    }

    /**
     * method for find the whjole with the same size
     *
     * @param size of a desired hole
     * @return the final count or the hole position
     */
    private int findCurrentHole(int size) {
        int result = -1;
        for (int i = 0; i < holes.size(); i++) {
            if (holes.get(i).getSize() >= size) {
                result = holes.get(i).getPosition();
                break;
            }
        }
        return result == -1 ? count : result;
    }

    public static void main(String[] args) throws HeapException {
        Heap2 h2 = new Heap2(20);
        h2.malloc(3);
        h2.malloc(3);
        h2.free(3);
        h2.malloc(6);
        h2.free(0);
        h2.malloc(7);
        h2.defrag();
        h2.malloc(6);
        System.out.println();
    }
}
