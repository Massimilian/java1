package ru.progwards.java2.lessons.gc;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Special heap keeper with priority queue, auto-defrag and holes-keeper systems
 */
public class Heap4 implements FatherHeap {
    byte[] bytes;
    PriorQueue<Brick> bricks = new PriorQueue<>();
    ArrayList<Hole> holes = new ArrayList<>();
    int count = 0;
    int defragCounter = 0;

    Heap4(int maxHeapSize) {
        this.bytes = new byte[maxHeapSize];
    }

    @Override
    public int malloc(int size) throws OutOfMemoryException {
        int startPos = -1;
        if (!holes.isEmpty()) {
            startPos = findPositionBySize(size);
        }
        Brick brick = new Brick(size);
        bricks.add(brick);
        if (startPos == -1) {
            add(size);
            startPos = count - size;
        } else {
            addBytes(size, startPos, false);
        }
        return startPos;
    }

    /**
     * Method for find and return the current position of the hole with current size
     *
     * @param size is a size of posible hole
     * @return hole position
     */
    private int findPositionBySize(int size) {
        int result = -1;
        Optional<Hole> opt = holes.stream().filter(x -> x.getSize() >= size).findFirst();
        if (opt.isPresent()) {
            Hole temp = opt.get();
            temp.setSize(temp.getSize() - size);
            temp.setPosition(temp.getPosition() + size);
            if (temp.getSize() == 0) {
                holes.remove(temp);
            }
            result = temp.getPosition() - size;
        }
        return result;
    }

    /**
     * Method for compact (if need) and add
     *
     * @param size
     * @throws OutOfMemoryException
     */
    private void add(int size) throws OutOfMemoryException {
        if (this.count + size > bytes.length) {
            compact();
        }
        addBytes(size, this.count, true);
    }

    /**
     * Compact method
     */
    private void compact() {
        bytes = new byte[bytes.length];
        count = 0;
        for (int i = 0; i < bricks.size(); i++) {
            for (int j = 0; j < bricks.get(i).size(); j++) {
                writeBrick(bricks.get(i).get(j));
            }
        }
    }

    /**
     * Method for put the brick into the queue
     *
     * @param brick is brick for put
     */
    private void writeBrick(Brick brick) {
        bytes[count++] = 2;
        for (int i = 0; i < brick.getCounter() - 1; i++) {
            bytes[count++] = 1;
        }
    }

    /**
     * Method for add bytes into the bytes array
     *
     * @param size        is a number of bytes
     * @param startPos    is a start position
     * @param changeCount is info about where we'll put the brick (at the end of array or not)
     * @throws OutOfMemoryException if has memory problems
     */
    private void addBytes(int size, int startPos, boolean changeCount) throws OutOfMemoryException {
        if (startPos + size <= bytes.length) {
            bytes[startPos++] = 2;
            for (int i = 0; i < size - 1; i++) {
                bytes[startPos++] = 1;
            }
            if (changeCount) {
                count = startPos;
            }
        } else {
            throw new OutOfMemoryException();
        }
    }

    @Override
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] == 2) {
            int size = delete(ptr);
            if (ptr + size != count) {
                holes.add(new Hole(size, ptr));
            } else {
                count -= size;
            }
            this.bricks.delete(size);
        } else {
            throw new InvalidPointerException();
        }
        defragCounter++;
        if (defragCounter == 10) {
            defrag();
        }
    }

    /**
     * Mwthod for defrag all holes
     */
    private void defrag() {
        holes = (ArrayList<Hole>) holes.stream().sorted().collect(Collectors.toList());
        int max = holes.size();
        for (int i = 0; i < max - 1; i++) {
            if (holes.get(i).getPosition() + holes.get(i).getSize() == holes.get(i + 1).getPosition()) {
                merge(i);
                i--;
                max--;
            }
        }
        if (holes.get(holes.size() - 1).getSize() + holes.get(holes.size() - 1).getPosition() == count) {
            count -= holes.get(holes.size() - 1).getSize();
            holes.remove(holes.get(holes.size() - 1));
        }
    }

    /**
     * Method for merge two nighbour holes
     *
     * @param i is a position of the "left" hole
     */
    private void merge(int i) {
        int size = holes.get(i).getSize() + holes.get(i + 1).getSize();
        holes.set(i, new Hole(size, holes.get(i).getPosition()));
        holes.remove(i + 1);
    }

    /**
     * Method for delete the info about object from bytes
     *
     * @param ptr is the start position of a object
     * @return the number of deleted bytes
     */
    private int delete(int ptr) {
        int count = 1;
        bytes[ptr++] = 0;
        while (ptr != this.bytes.length && bytes[ptr] == 1) {
            bytes[ptr++] = 0;
            count++;
        }
        return count;
    }

    @Override
    public void clear() {
        bytes = new byte[bytes.length];
        holes.clear();
        bricks.clear();
        count = 0;
    }
}
