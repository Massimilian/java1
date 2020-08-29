package ru.progwards.java2.lessons.gc.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Heap {
    private boolean hasHole = false;
    private boolean checked = true;
    private byte[] bytes;
    private int position = 0;
    private final byte point = 1; // continue of a Block
    private final byte startBlock = 10; // start of a Block
    private ArrayList<Block> blocks = new ArrayList<>(); // list of added Blocks
    private final String separator = System.lineSeparator();

    public Heap(int size) {
        this.bytes = new byte[size];
    }

    /*
    Information about status of memory (keap)
    */
    public String status() {
        return String.format("Occupired memory: %d;%sfree memory:%d;%schecked: %b.%s-----", this.position, this.separator, this.bytes.length - this.position, this.separator, this.checked, this.separator);
    }

    /*
    Adding a new Block to the Heap if it possible, and compacting the heap, if not.
     */
    public int malloc(int size) throws OutOfMemoryException {
        if (this.position + size > bytes.length) { // if out of memory - compact
            this.compact();
        }
        if (this.position + size > bytes.length) { // if is out of memory after compact - out of memory
            throw new OutOfMemoryException();
        }
        blocks.add(new Block(size, position)); // add new Block into list of Blocks
        int result = position;
        bytes[position] = startBlock; // begin to add the Block into heap
        for (int i = position + 1; i < size + position; i++) { // continue to add the Block into heap
            bytes[i] = point;
        }
        position += size; // renovate position
        this.checked = false;
        return result;
    }

    /*
    Deleting the block from the Heap
     */
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] != startBlock) { // is position (ptr) valid
            throw new InvalidPointerException();
        } else {
            blocks.remove(new Block(ptr)); // remove the Block from the Block list
            do {
                bytes[ptr++] = 0;
                if (bytes[ptr] == 0) {
                    break;
                }
            } while (bytes[ptr] != startBlock);
        }
        this.checked = false;
        this.hasHole = isHasHole(ptr); // check the hoke
    }

    /*
    Looking for the holes after the position
     */
    private boolean isHasHole(int ptr) {
        boolean result = false;
        for (int i = ptr; i < bytes.length; i++) {
            if(bytes[i] != 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    /*
    Method for merge blocks, if they are neighbours.
     */
    public void defrag() {
        for (int i = 1; i < bytes.length; i++) {
            if (bytes[i - 1] != 0 && bytes[i] == startBlock) {
                bytes[i] = point;
            }
        }
    }

    /*
    Most easy way for compacting
     */
    public void compactEasy() { // most easy compacting by all Blocks one-by-one (but very laborious)
        byte[] newBytes = new byte[bytes.length];
        int count = 0;
        for (Block block : blocks) {
            int temp = count;
            newBytes[count++] = this.startBlock;
            for (int j = count; j < block.getSize() + temp; j++) {
                newBytes[j] = point;
                count++;
            }
        }
        bytes = newBytes;
        this.changeDates();
    }

    /*
    Main compact of the Heap
     */
    public void compact() {
        if (!checked) {
            prepare();
        }
        boolean checked = false;
        int check = 0;
        int currentPos = -1; // for start position
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                currentPos = !checked ? i : currentPos;
                check++;
                checked = true;
            } else {
                checked = false;
            }
            if (!checked && currentPos != -1) {
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

    /*
    Preparing the List of Blocks for using
     */
    private void prepare() {
        blocks = (ArrayList<Block>) blocks.stream().sorted().collect(Collectors.toList()); // sort blocks
        blocks.forEach(x -> x.setMoved(false)); // changing parameter "moved"
        Collections.reverse(blocks); // right sorting
    }

    /*
    Compressing the Heap by puting Blocks into the holes
     */
    private void compress(int thisPosition, int size) {
        // check the block with the same size
        ArrayList<Block> changedBlocks = (ArrayList<Block>) blocks.stream().filter(x -> x.getSize() <= size && !x.isMoved() && x.getPosition() > thisPosition).limit(1).collect(Collectors.toList());
        if (changedBlocks.size() != 0) { // if we have this block...
            this.add(thisPosition, changedBlocks.get(0).getSize()); // add it into hole
            this.delete(changedBlocks.get(0)); // delete the duplicate
            changedBlocks.get(0).setMoved(true); // check it as moved Block
        }
    }

    /*
    Adding the Block into the hole
     */
    private void add(int currentPos, int size) {
        bytes[currentPos] = startBlock;
        for (int i = currentPos + 1; i < currentPos + size; i++) {
            bytes[i] = point;
        }
    }

    /*
    Deleting the Block from the Heap
     */
    private void delete(Block block) {
        for (int i = block.getPosition(); i < block.getPosition() + block.getSize(); i++) {
            bytes[i] = 0;
        }
    }

    /*
    puting the block into the most "right" position
     */
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

    /*
    Changing dates of all the hole.
     */
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
