package ru.progwards.java2.lessons.gc_konst;

/**
 * Задача 1. class Heap - имитация менеджера памяти
 * Имеется массив байт, который будет представлять из себя кучу - heap. Нужно будет написать алгоритм,
 * который выделяет и освобождает память (ячейки в массиве) и делает дефрагментацию.
 *
 * 1. Создать конструктор Heap(int maxHeapSize)
 *
 * 2. Создать свойство класса byte[] bytes; - собственно, куча
 *
 * 2. Метод public int malloc(int size) - "размещает", т.е. помечает как занятый блок памяти с количеством ячеек
 * массива heap равным size. Соответственно это должен быть непрерывный блок (последовательность ячеек),
 * которые на момент выделения свободны. Возвращает "указатель" - индекс первой ячейки в массиве, размещенного блока.
 *
 * 3. Метод public void free(int ptr) - "удаляет", т.е. помечает как свободный блок памяти по "указателю".
 * Проверять валидность указателя - т.е. то, что он соответствует началу ранее выделенного блока, а не его середине,
 * или вообще, уже свободному.
 *
 * 4. Метод public void defrag() - осуществляет дефрагментацию кучи - ищет смежные свободные блоки, границы
 * которых соприкасаются и которые можно слить в один.
 *
 * 5. Метод public void compact() - компактизация кучи - перенос всех занятых блоков в начало хипа, с копированием
 * самих данных - элементов массива. Для более точной имитации производительности копировать просто в цикле по одному элементу,
 * не используя System.arraycopy. Обязательно запускаем compact из malloc если не нашли блок подходящего размера
 *
 * 6. Исключения - свои собственные
 *
 * OutOfMemoryException - нет свободного блока подходящего размера
 * InvalidPointerException - неверный указатель. Возникает при освобождении блока,
 * если переданный указатель не является началом блока
 * Для реализации этих методов надо будет завести структуру данных - список (или другая структура данных) свободных блоков.
 * При выделении памяти искать блок подходящего размера в этом списке, при освобождении - добавлять его туда.
 * Для проверки валидности освобождения указателей - список (или другая структура данных) занятых блоков.
 * При компактизации саму процедуру замены старый указателей на новые опускаем, поэтому и делаем не очень
 * эффективное копирование самих данных, что бы была близкая производительность.
 */

import java.util.*;

public class Heap {
    private int maxHeapSize;
    private byte[] bytes;
    ArrayList<MemoryBlock> free;
    TreeMap<Integer, MemoryBlock> used;
    int magicSortNumber =10_000_000;

    public Heap(int maxHeapSize) {
        this.maxHeapSize = maxHeapSize;
        bytes = new byte[maxHeapSize];
        free = new ArrayList<>();
        free.add(new MemoryBlock(0, maxHeapSize));
        used = new TreeMap<>(Integer::compareTo);
    }

    public int malloc(int size) throws OutOfMemoryException {
        int ptr = mallocOneBlock(size);
        if (ptr < 0) {
            compact();
            ptr=  mallocOneBlock(size);
            if (ptr<0) {
                throw new OutOfMemoryException("Нет свободных блоков для ", size);
            }
        }
        return ptr;
    }

    public int mallocOneBlock(int size) {
        int ptr = fastFindFreeBlock(size);
        if (ptr < 0) {
            return -1;
        }
        MemoryBlock block = free.get(ptr);
        if (block.size == size) {
            used.put(block.ptr, block);
            free.remove(ptr);
            return block.ptr;
        }
        MemoryBlock useMB = new MemoryBlock(block.ptr,size);
        used.put(useMB.ptr, useMB);
        block.ptr = block.ptr + size;
        block.size = block.size - size;
        if (block.size == 0) {
            free.remove(ptr);
        } else {
            //Сделано для оптимизации - нельзя всякий раз при аллоцировании
            //запускать сортировку свободного пространства - в этом нет никакого смысла
            //только тратится время.
            if (free.size() % magicSortNumber == 0) {
                free.sort(Comparator.comparing(a -> a.size));
            }
        }
        return useMB.ptr;
    }

    private int fastFindFreeBlock(int size) {
        int ptr = -1;
        int beg = 0;
        int end = free.size() -1;
        while (beg <= end) {
            int mid = (beg + end) / 2;
            MemoryBlock block = free.get(mid);
            if (block.size < size) {
                beg = mid + 1;
            } else if (block.size > size) {
                end = mid - 1;
                ptr = mid;
            } else {
                ptr = mid;
                break;
            }
        }
        return ptr;
    }

    public void free(int ptr) throws InvalidPointerException {
        MemoryBlock block = used.get(ptr);
        if (block== null) {
            throw new InvalidPointerException("Неверный адрес участка памяти", ptr);
        }
        free.add(block);
        free.sort(Comparator.comparingInt(a -> a.size));
        used.remove(ptr);
    }
    public void defrag() {
        ListIterator<MemoryBlock> iterator = free.listIterator();
        MemoryBlock curr;
        MemoryBlock prev = iterator.next();
        while (iterator.hasNext()) {
            curr = iterator.next();
            if ((prev.ptr + prev.size)== curr.ptr) {
                prev.size += curr.size;
                iterator.remove();;
            }  else  {
                prev = curr;
            }
        }
    }

    public void compact() {

        if (used.size()==0) {
            free.clear();
            free.add(new MemoryBlock(0, maxHeapSize));
            return;
        }

        MemoryBlock fst = used.firstEntry().getValue();
        int d = fst.ptr;
        if ( d>0) {
            moveBlock(fst,d);
        }
        MemoryBlock prev = fst;
        for (MemoryBlock item : used.values()) {
            d = item.ptr - (prev.ptr + prev.size);
            if (d > 0) {
                moveBlock(item, d);
            }
            prev = item;
        }
        free.clear();
        MemoryBlock mbLast = used.lastEntry().getValue();
        int ptr = mbLast.ptr + mbLast.size;
        free.add(new MemoryBlock(ptr,maxHeapSize-ptr));
    }


    private void moveBlock(MemoryBlock mb, int d) {
        for (int i = mb.ptr; i < mb.ptr + mb.size ; i++) {
            bytes[i -d ]= bytes[i];
        }
        mb.ptr -= d;
    }
}
