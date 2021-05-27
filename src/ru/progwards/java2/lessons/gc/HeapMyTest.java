package ru.progwards.java2.lessons.gc;

import java.time.Duration;
import java.time.Instant;

public class HeapMyTest {
    /**
     * 1000 malloc and half-free tests
     *
     * @param heap heap for test
     * @param name name of heap
     * @throws HeapException
     */
    public static void thousandTestsMallocAndHalfFree(FatherHeap heap, String name) throws HeapException {
        Instant start = Instant.now();
        for (int i = 0; i < 1000; i++) {
            testFree(heap);
            heap.clear();
        }
        Instant finish = Instant.now();
        Duration res = Duration.between(start, finish);
        System.out.println(name + " (test free method): " + res.getSeconds() + "." + res.getNano());
    }

    /**
     * Free method test
     *
     * @param heap heap for testp
     * @throws HeapException
     */
    public static void testFree(FatherHeap heap) throws HeapException {
        for (int i = 1; i < 40; i++) {
            for (int j = 1; j < 40; j++) {
                heap.malloc(j);
            }
        }
        int pos = 0;
        boolean add = true;
        for (int i = 1; i < 40; i++) {
            for (int j = 1; j < 40; j++) {
                pos += j - 1;
                if (add) {
                    heap.free(pos);
                }
                add = !add;
            }
            pos += 39;
        }
    }

    /**
     * 1000 malloc test
     *
     * @param heap heap for test
     * @param name name of heap
     * @throws OutOfMemoryException
     */
    public static void thousandTestsMalloc(FatherHeap heap, String name) throws OutOfMemoryException {
        Instant start = Instant.now();
        for (int i = 0; i < 1000; i++) {
            testMalloc(heap);
            heap.clear();
        }
        Instant finish = Instant.now();
        Duration res = Duration.between(start, finish);
        System.out.println(name + " (test malloc method): " + res.getSeconds() + "." + res.getNano());
    }

    /**
     * Malloc method test
     *
     * @param heap heap for test
     * @throws OutOfMemoryException
     */
    private static void testMalloc(FatherHeap heap) throws OutOfMemoryException {
        for (int i = 1; i < 40; i++) {
            for (int j = 1; j < 40; j++) {
                heap.malloc(j);
            }
        }
    }

    /**
     * 100 test of malloc-free-malloc methods (big files)
     *
     * @param heap heap for test
     * @param name name of heap
     * @throws HeapException
     */
    public static void hundredTestsMallocAndFreeAndAddBigFiles(FatherHeap heap, String name) throws HeapException {
        Instant start = Instant.now();
        for (int i = 0; i < 100; i++) {
            testMallocAndFreeAndAddFiles(heap, 10, 1522);
            heap.clear();
        }
        Instant finish = Instant.now();
        Duration res = Duration.between(start, finish);
        System.out.println(name + " test malloc, free and new malloc (big files) method: " + res.getSeconds() + "." + res.getNano());
    }

    /**
     * 100 test of malloc-free-malloc methods (small files)
     *
     * @param heap heap for test
     * @param name name of heap
     * @throws HeapException
     */
    public static void hundredTestsMallocAndFreeAndAddSmallFiles(FatherHeap heap, String name) throws HeapException {
        Instant start = Instant.now();
        for (int i = 0; i < 100; i++) {
            testMallocAndFreeAndAddFiles(heap, 3044, 5);
            heap.clear();
        }
        Instant finish = Instant.now();
        Duration res = Duration.between(start, finish);
        System.out.println(name + " test malloc, free and new malloc (small files) method: " + res.getSeconds() + "." + res.getNano());
    }

    /**
     * Method for malloc-free-malloc test
     *
     * @param heap  heap for test
     * @param small number of adds
     * @param big   size of an objwct
     * @throws HeapException
     */
    private static void testMallocAndFreeAndAddFiles(FatherHeap heap, int small, int big) throws HeapException {
        testFree(heap);
        for (int i = 0; i < small; i++) {
            heap.malloc(big);
            heap.clear();
        }
    }

    public static void main(String[] args) throws HeapException {
        Heap heap = new Heap(30420);
        Heap2 heap2 = new Heap2(30420);
        System.out.println("1. Testing malloc method (1000 actions).");
        thousandTestsMalloc(heap, "Heap-1");
        thousandTestsMalloc(heap2, "Heap-2");
        System.out.println("2. Testing malloc & free (not all) method (1000 actions).");
        thousandTestsMallocAndHalfFree(heap, "Heap-1");
        thousandTestsMallocAndHalfFree(heap2, "Heap-2");
        System.out.println("3. Testing malloc & free (not all) and new malloc (big files) method (100 actions).");
        hundredTestsMallocAndFreeAndAddBigFiles(heap, "Heap-1");
        hundredTestsMallocAndFreeAndAddBigFiles(heap2, "Heap-2");
        System.out.println("4. Testing malloc & free (not all) and new malloc (small files) method (100 actions).");
        hundredTestsMallocAndFreeAndAddSmallFiles(heap, "Heap-1");
        hundredTestsMallocAndFreeAndAddSmallFiles(heap2, "Heap-2");
    }
}
