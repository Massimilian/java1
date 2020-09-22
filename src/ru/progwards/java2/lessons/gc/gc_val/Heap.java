package ru.progwards.java2.lessons.gc_val;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;

class InvalidPointerException extends Exception {
}

public class Heap {
    final static byte FREE = 0;
    final static byte BUSY = 1;
    final static byte MAGIC = (byte)0xAA;
    final static int HEADER_SIZE = 10;

    /*class Block {
        int size;
        int ptr;
    }*/

    byte[] bytes;
    int [] bsize;
    int [] bptr;
    int maxsize;
    int asize;
    int lastptr;

    int maxasize;

    Heap(int maxHeapSize) {
        bytes = new byte[maxHeapSize+maxHeapSize/10];
        int n = maxHeapSize/10/HEADER_SIZE;
        bsize = new int[n];
        bptr = new int[n];
        asize = 0;
        maxsize = bytes.length;
        int ptr = HEADER_SIZE;
        setHeaderMagic(ptr,MAGIC);
        setHeaderBusy(ptr, FREE);
        setHeaderPrev(ptr, 0);
        setHeaderSize(ptr, maxsize-HEADER_SIZE);
        lastptr = HEADER_SIZE;
    }

    public int malloc(int size) throws InvalidPointerException {
        int ptr;
//        if (lastptr != 0 && getHeaderBusy(lastptr) == FREE && getHeaderSize(lastptr) >= size+HEADER_SIZE)
//            ptr = lastptr;
//        else
            ptr = findBlockFull(size);
        int current = getHeaderSize(ptr);
        int curnext = ptr + current + HEADER_SIZE;
        setHeaderBusy(ptr, BUSY);
        if (current > size+HEADER_SIZE) {
            setHeaderSize(ptr, size);
            int nextptr = ptr + size + HEADER_SIZE;
            setHeaderMagic(nextptr, MAGIC);
            setHeaderBusy(nextptr, FREE);
            setHeaderPrev(nextptr, size);
            int newsize = current-size-HEADER_SIZE;
            setHeaderSize(nextptr, newsize);
            fixNextSize(nextptr, newsize);

            insertBlock(nextptr);
        }
//        int bad = checkHeap();
//        if (bad != 0) {
//            System.out.println("---- bad block "+bad);
//            printBlocks();
//        }
//        lastptr = ptr;
        return ptr;
    }

    public void free(int ptr) throws InvalidPointerException {
        if (bad(ptr) || getHeaderBusy(ptr) != BUSY)
            throw new InvalidPointerException();
        setHeaderBusy(ptr, FREE);
        defrag(ptr);
        //insertBlock(ptr); // no defrag
    }

    int findBlock(int size) throws InvalidPointerException {
        int ptr = HEADER_SIZE;
        int current = getHeaderSize(ptr);
        while (getHeaderBusy(ptr) == BUSY || size+HEADER_SIZE > current) {
            ptr += current + HEADER_SIZE;
            if (ptr > maxsize)
                throw new InvalidPointerException();//OutOfMemoryError();
            current = getHeaderSize(ptr);
        }
        return ptr;
    }

    void defrag(int ptr) throws InvalidPointerException {
        int size = getHeaderSize(ptr);
        int prev = getHeaderPrev(ptr);
        // if not first block defrag prev
        if (prev != 0) {
            int prevptr = ptr - prev - HEADER_SIZE;
            if (bad(prevptr))
                throw new InvalidPointerException();
            if (getHeaderBusy(prevptr) == FREE) {
                removeBlock(prevptr);

                size += prev + HEADER_SIZE;
                setHeaderSize(prevptr, size);
                fixNextSize(prevptr, size);
                ptr = prevptr;
            }
        }
        int nextptr = ptr + size + HEADER_SIZE;
        // if not last block defrag next
        if (nextptr <  maxsize) {
            if (bad(nextptr))
                throw new InvalidPointerException();
            if (getHeaderBusy(nextptr) == FREE) {
                removeBlock(nextptr);

                size += getHeaderSize(nextptr) + HEADER_SIZE;
                setHeaderSize(ptr, size);
                fixNextSize(ptr, size);
            }
        }
        insertBlock(ptr);
    }

    void fixNextSize(int ptr, int size) {
        int nextptr = ptr + size + HEADER_SIZE;
        if (nextptr < maxsize)
            setHeaderPrev(nextptr, size);
        else if (getHeaderBusy(ptr) == FREE)
            lastptr = ptr;
    }

    boolean bad(int ptr) {
        return  ptr < HEADER_SIZE || getHeaderMagic(ptr) != MAGIC || ptr + getHeaderSize(ptr) > maxsize;
    }

    /*
    Header
    [0] - MAGIC
    [1] - FREE / BUSY
    [2] --
    [3]
    [4]
    [5] prev_size
    [6]
    [7]
    [8]
    [9] next_size
     */


    byte getHeaderMagic(int ptr) {
        return bytes[ptr-HEADER_SIZE];
    }

    byte getHeaderBusy(int ptr) {
        return bytes[ptr-HEADER_SIZE+1];
    }

    int getHeaderPrev(int ptr) {
        return bytes2int(ptr-8);
    }

    int getHeaderSize(int ptr) {
        return bytes2int(ptr-4);
    }

    void setHeaderMagic(int ptr, byte magic) {
        bytes[ptr-HEADER_SIZE] = magic;
    }

    void setHeaderBusy(int ptr, byte busy) {
        bytes[ptr-HEADER_SIZE+1] = busy;
    }

    void setHeaderPrev(int ptr, int prev) {
        int2bytes(prev,ptr-8);
    }

    void setHeaderSize(int ptr, int size) {
        if (size == 0)
            System.out.println("size=0");
        int2bytes(size,ptr-4);
    }

    void int2bytes(int n, int index) {
        bytes[index++] = (byte)n;
        n >>= 8;
        bytes[index++] = (byte)n;
        n >>= 8;
        bytes[index++] = (byte)n;
        n >>= 8;
        bytes[index] = (byte)n;
    }

    int bytes2int(int index) {
        int n = bytes[index+3] & 0xFF;
        n <<= 8;
        n |= bytes[index+2] & 0xFF;
        n <<= 8;
        n |= bytes[index+1] & 0xFF;
        n <<= 8;
        n |= bytes[index] & 0xFF;
        return n;
    }

    public void getBytes(int ptr, byte[] bytes) {
        //System.arraycopy(this.bytes, ptr, bytes, 0, size);
    }

    public void setBytes(int ptr, byte[] bytes) {
        //System.arraycopy(bytes, 0, this.bytes, ptr, size);
    }

    int findBlock2(int key) {
        int min = 0;
        int max = asize - 1;
        int i = max;
        while (max > min) {
            i = (max + min) / 2;
            int cmp = Integer.compare(key, bsize[i]);
            if (cmp == 0)
                return i;
            if (cmp < 0)
                max = i;
            else
                min = i + 1;
        }
        int cmp = Integer.compare(key, bsize[i]);
        if (cmp > 0)
            i++;
        return i;
    }

    int findBlockDefrag(int size) throws InvalidPointerException {
        if (asize == 0)
            fillSizes();

        int n = findBlock2(size+HEADER_SIZE);
        int ptr = bptr[n];
        if (bsize[n] < size+HEADER_SIZE)
            defrag();
        n = findBlock2(size+HEADER_SIZE);
        ptr = bptr[n];
        if (bsize[n] < size+HEADER_SIZE)
            throw new InvalidPointerException();//OutOfMemoryError();
        removeBlockAt(n);
        return ptr;
    }

    void defrag() throws InvalidPointerException {
        int ptr = HEADER_SIZE;
        while (ptr < maxsize) {
            if (getHeaderBusy(ptr) == FREE) {
                defrag(ptr);
            }
            ptr += getHeaderSize(ptr) + HEADER_SIZE;
        }
    }

    int findBlockFull(int size) throws InvalidPointerException {
        if (asize == 0)
            fillSizes();

        int n = findBlock2(size+HEADER_SIZE);
        int ptr = bptr[n];
        if (bsize[n] < size+HEADER_SIZE)
            throw new InvalidPointerException();//OutOfMemoryError();
        removeBlockAt(n);
        return ptr;
    }

    int findBlockPtr(int ptr) throws InvalidPointerException {
        int size = getHeaderSize(ptr);
        int n = findBlock2(size);
        if (bptr[n] == ptr)
            return n;
        int i = n;
        while (i > 0 && size == bsize[--i])
            if (bptr[i] == ptr)
                return i;
        i = n;
        while (i < asize-1 && size == bsize[++i])
            if (bptr[i] == ptr)
                return i;
        throw new InvalidPointerException();
    }

    void fillSizes() {
        int ptr = HEADER_SIZE;
        while (ptr < maxsize) {
            if (getHeaderBusy(ptr) == FREE) {
                //bsize[asize] = getHeaderSize(ptr);
                //bptr[asize++] = ptr;
                insertBlockForce(ptr);
            }
            ptr += getHeaderSize(ptr) + HEADER_SIZE;
        }
    }

    int lastBlock() {
        int ptr = HEADER_SIZE, prev = ptr;
        while (ptr < maxsize) {
            prev = ptr;
            ptr += getHeaderSize(ptr) + HEADER_SIZE;
        }
        return prev;
    }

    int checkHeap() {
        int ptr = HEADER_SIZE;
        while (ptr < maxsize) {
            if (bad(ptr))
                return ptr;
            ptr += getHeaderSize(ptr) + HEADER_SIZE;
        }
        return 0;
    }

    void insertBlock(int ptr) {
        insertBlock(ptr, asize!=0);
    }

    void insertBlockForce(int ptr) {
        insertBlock(ptr, true);
    }

    void insertBlock(int ptr, boolean insert) {
        if (!insert)
            return;

        int size = getHeaderSize(ptr);
        int n;
        if (asize > 0) {
            n = findBlock2(size);
            if (n == asize - 1 && size >= bsize[n])
                n++;
            else {
                System.arraycopy(bsize, n, bsize, n + 1, asize - n);
                System.arraycopy(bptr, n, bptr, n + 1, asize - n);
            }
        }
        else
            n = 0;
        bptr[n] = ptr;
        bsize[n] = size;
        asize++;
        maxasize = Math.max(maxasize, asize);
    }

    void removeBlockAt(int n) {
        System.arraycopy(bsize, n+1, bsize, n, --asize-n);
        System.arraycopy(bptr, n+1, bptr, n, asize-n);
    }

    void removeBlock(int ptr) throws InvalidPointerException {
        if (asize == 0)
            return;
        removeBlockAt(findBlockPtr(ptr));
    }

    void printBlocks() {
        System.out.println(Arrays.toString(Arrays.copyOf(bsize,asize)));
        System.out.println(Arrays.toString(Arrays.copyOf(bptr,asize)));
        int ptr = HEADER_SIZE;
        while (ptr < maxsize) {
            printBlock(ptr);
            ptr += getHeaderSize(ptr) + HEADER_SIZE;
        }
    }

    public void logBlocks() {
        log(Arrays.toString(Arrays.copyOf(bsize,asize)));
        log(Arrays.toString(Arrays.copyOf(bptr,asize)));
        int ptr = HEADER_SIZE;
        while (ptr < maxsize) {
            logBlock(ptr);
            ptr += getHeaderSize(ptr) + HEADER_SIZE;
        }
    }

    void printBlock(int ptr) {
        System.out.println("addr: "+ptr+" magic: "+getHeaderMagic(ptr)+" busy: "+getHeaderBusy(ptr)+" prev: "+getHeaderPrev(ptr)+" size: "+getHeaderSize(ptr));
    }

    void logBlock(int ptr) {
        log("addr: "+ptr+" magic: "+getHeaderMagic(ptr)+" busy: "+getHeaderBusy(ptr)+" prev: "+getHeaderPrev(ptr)+" size: "+getHeaderSize(ptr));
    }

    public static PrintWriter logfile = null;
    public static void initLog(String filename) {
        try {
            logfile = new PrintWriter(new FileOutputStream(new File(filename)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeLog() {
        if (logfile != null)
            logfile.close();
    }

    public static void log(String str) {
        log2(str, true);
    }

    public static void log2(String str, boolean log) {
        //System.out.println(str);
        if (log) {
            try {
                logfile.println(LocalDateTime.now() + ": " + str);
            } catch (Exception e) {
                e.printStackTrace();
                logfile.close();
            }
        }
    }

    public static void main(String[] args) throws InvalidPointerException {
        Heap heap = new Heap(1200);
        heap.printBlocks();
        int ptr1 = heap.malloc(100);
        System.out.println("ptr1: "+ptr1);
        heap.printBlocks();
        int ptr2 = heap.malloc(200);
        System.out.println("ptr2: "+ptr2);
        heap.printBlocks();
        int ptr3 = heap.malloc(333);
        System.out.println("ptr3: "+ptr3);
        heap.printBlocks();
        heap.free(ptr2);
        System.out.println("free ptr2");
        heap.printBlocks();
        ptr2 = heap.malloc(100);
        System.out.println("---ptr2: "+ptr2);
        heap.printBlocks();
        heap.malloc(55);
        heap.malloc(22);
        heap.malloc(400);
        heap.printBlocks();
        heap.free(673);
        //heap.free(848);
        heap.printBlocks();
        System.out.println(heap.malloc(90));
        heap.printBlocks();
        heap.free(330);
        heap.printBlocks();
        heap.free(10);
        heap.printBlocks();
        heap.free(705);//880);
        heap.printBlocks();
        heap.free(120);//673);
        heap.printBlocks();
        heap.free(230);//783);
        heap.printBlocks();

        heap.free(1115);//783);
        heap.printBlocks();
        //---------------
        heap.malloc(100);
        heap.printBlocks();
        heap.malloc(1150);
        heap.printBlocks();
        heap.free(10);
        heap.printBlocks();
        heap.malloc(80);
        heap.printBlocks();

//        int ptr4 = heap.malloc(80);
//        System.out.println("ptr4: "+ptr4);
//        heap.printBlocks();
//        int ptr5 = heap.malloc(460);
//        System.out.println("ptr5: "+ptr3);
//        heap.printBlocks();
//        heap.free(ptr2);
//        System.out.println("free ptr2 "+ptr2);
//        heap.printBlocks();
//        heap.free(ptr3);
//        System.out.println("free ptr3 "+ptr3);
//        heap.printBlocks();
//        heap.free(ptr1);
//        System.out.println("free ptr1 "+ptr1);
//        heap.printBlocks();
//        ptr1 = heap.malloc(100);
//        System.out.println("ptr1: "+ptr1);
//        heap.printBlocks();
//        heap.free(ptr5);
//        System.out.println("free ptr5 "+ptr5);
//        heap.printBlocks();
//        heap.free(ptr4);
//        System.out.println("free ptr4 "+ptr4);
//        heap.printBlocks();

    }
}
