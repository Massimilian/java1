package ru.progwards.java2.lessons.gc;

/**
 * Interface of different types of heap
 */
public interface FatherHeap {
    /**
     * Add new object into the memory array (bytes)
     *
     * @param size is a number of positions into the array
     * @return start position of a new object into the memory
     * @throws OutOfMemoryException
     */
    int malloc(int size) throws OutOfMemoryException, InvalidPointerException;


    /**
     * Method for delete memory block
     *
     * @param ptr position of a block for delete
     * @throws InvalidPointerException
     */
    void free(int ptr) throws InvalidPointerException;

    /**
     * Method to clear and restart all memory system
     */
    void clear();
}
