package ru.progwards.java2.lessons.gc.heap;

import java.util.Objects;

public class Block implements Comparable<Block> {
    private int size;
    private int position;
    private boolean moved = false;

    public Block(int size, int position) {
        this.size = size;
        this.position = position;
    }

    public Block(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public int getPosition() {
        return position;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return position == block.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }


    @Override
    public int compareTo(Block o) {
        return this.getSize() - o.getSize();
    }
}
