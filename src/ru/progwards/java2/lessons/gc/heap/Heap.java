package ru.progwards.java2.lessons.gc.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Heap {
    private boolean hasHole = false;
    private boolean checked = true;
    private byte[] bytes;
    private int position = 0;
    private final byte point = 1;
    private final byte startBlock = 10;
    private ArrayList<Block> blocks = new ArrayList<>();
    private String separator = System.lineSeparator();

    public Heap(int size) {
        this.bytes = new byte[size];
    }

    public String status() {
        return String.format("Occupired memory: %d;%sfree memory:%d;%schecked: %b.%s-----", this.position, this.separator, this.bytes.length - this.position, this.separator, this.checked, this.separator);
    }

    public int malloc(int size) throws OutOfMemoryException {
        if (this.position + size > bytes.length) {
            this.compact();
        }
        if (this.position + size > bytes.length) {
            throw new OutOfMemoryException();
        }
        blocks.add(new Block(size, position));
        int result = position;
        bytes[position] = startBlock;
        for (int i = position + 1; i < size + position; i++) {
            bytes[i] = point;
        }
        position += size;
        this.checked = false;
        return result;
    }

    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] != startBlock) {
            throw new InvalidPointerException();
        } else {
            blocks.remove(new Block(ptr));
            do {
                bytes[ptr++] = 0;
                if (bytes[ptr] == 0) {
                    break;
                }
            } while (bytes[ptr] != startBlock);
        }
        this.checked = false;
        this.hasHole = true;
    }

    public void defrag() {

    }

    public void compactEasy() {
        byte[] newBytes = new byte[bytes.length];
        int count = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int temp = count;
            newBytes[count++] = this.startBlock;
            for (int j = count; j < blocks.get(i).getSize() + temp; j++) {
                newBytes[j] = point;
                count++;
            }
        }
        bytes = newBytes;
        this.changeDates();
    }

    public void compact() {
        if (!checked) {
            prepare();
        }
        boolean checked = false;
        int check = 0;
        int currentPos = -1;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                currentPos = !checked ? i : currentPos;
                check++;
                checked = true;
            } else {
                checked = false;
            }
            if (checked == false && currentPos != -1) {
                compress(currentPos, check);
                i = currentPos;
                currentPos = -1;
                check = 0;
            }
        }
        if (hasHole) {
            this.putLast();
        }
        this.changeDates();
    }

    private void prepare() {
        blocks = (ArrayList<Block>) blocks.stream().sorted().collect(Collectors.toList());
        blocks.stream().forEach(x -> x.setMoved(false));
        Collections.reverse(blocks);
    }

    private void compress(int thisPosition, int size) {
        ArrayList<Block> changedBlocks = (ArrayList<Block>) blocks.stream().filter(x -> x.getSize() <= size && x.isMoved() == false && x.getPosition() > thisPosition).limit(1).collect(Collectors.toList());
        if (changedBlocks.size() != 0) {
            this.add(thisPosition, changedBlocks.get(0).getSize());
            this.delete(changedBlocks.get(0));
            changedBlocks.get(0).setMoved(true);
        }
    }

    private void add(int currentPos, int size) {
        bytes[currentPos] = startBlock;
        for (int i = currentPos + 1; i < currentPos + size; i++) {
            bytes[i] = point;
        }
    }

    private void delete(Block block) {
        for (int i = block.getPosition(); i < block.getPosition() + block.getSize(); i++) {
            bytes[i] = 0;
        }
    }

    private void putLast() {
        int lastStart = 0;
        int size = 0;
        for (int i = this.position - 1; i >= 0; i--) {
            size++;
            if (bytes[i] == startBlock) {
                lastStart = i;
                break;
            }
        }
        if (bytes[lastStart - 1] == 0) {
            int newPos = lastStart - 1;
            int interval = 0;
            while (newPos >= 1 && bytes[newPos - 1] == 0) {
                newPos--;
                interval++;
            }
            bytes[newPos] = startBlock;
            for (int i = newPos + 1; i < newPos + size; i++) {
                bytes[i] = this.point;
            }
            for (int i = newPos + size; i <= newPos + size + interval; i++) {
                bytes[i] = 0;
            }
        }
    }

    private void changeDates() {
        int pos = 0;
        while (pos != bytes.length && bytes[pos] != 0) {
            pos++;
        }
        this.position = pos;
        this.checked = true;
        this.hasHole = false;
    }
}
