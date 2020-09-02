package ru.progwards.java2.lessons.gc.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Класс для имитации работы Garbage Collector
 */
public class Heap {
    private boolean hasHole = false; // проверка на наличик "дып" в памяти
    private boolean checked = true; // проверка на упорядоченность всей памяти
    private byte[] bytes; // собственно память
    private int position = 0; // текущая позиция заполнения памяти
    private final byte point = 1; // Обозначение занятого блока памяти
    private final byte startBlock = 10; // Обозначение начала занятого блока памяти
    private ArrayList<Block> blocks = new ArrayList<>(); // список блоков, которые находятся внутри памяти
    private final String separator = System.lineSeparator();

    /**
     * Конструктор со входным параметром "размер"
     *
     * @param size (размер памяти)
     */
    public Heap(int size) {
        this.bytes = new byte[size];
    }

    /**
     * Информация о состоянии памяти (в текстовом формате)
     *
     * @return (статус)
     */
    public String status() {
        return String.format("Occupired memory: %d;%sfree memory:%d;%schecked: %b.%s-----", this.position, this.separator, this.bytes.length - this.position, this.separator, this.checked, this.separator);
    }

    /**
     * Добавление нового объекта в память, если это возможно, и очистка, если нет.
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

    /**
     * Удаление занятого блока из памяти
     * @param ptr (текущая стартовая позиция)
     * @throws InvalidPointerException (если позиция не корректна)
     */
    public void free(int ptr) throws InvalidPointerException {
        if (bytes[ptr] != startBlock) { // проверка на валидность позиции
            throw new InvalidPointerException();
        } else {
            blocks.remove(new Block(ptr)); // Удаление занятого блока из памяти
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

    /**
     * Проверка на наличие свободных пространств внутри памяти
     * @param ptr (номер блока для проверки)
     * @return (информация о свободе блока)
     */
    private boolean isHasHole(int ptr) {
        boolean result = false;
        for (int i = ptr; i < bytes.length; i++) {
            if (bytes[i] != 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * @deprecated
     * Объединение двух блоков, если они соседи
     */
    public void defrag() {
        for (int i = 1; i < bytes.length; i++) {
            if (bytes[i - 1] != 0 && bytes[i] == startBlock) {
                bytes[i] = point;
            }
        }
    }

    /**
     * @deprecated
     * Наиболее простой способ компактизации блоков путём полной перезаписи памяти (трудоёмкий)
     */
    public void compactEasy() {
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

    /**
     * более быстрая компактизация памяти через перезаписывание ячеек памяти в пустые слоты, подходящие по размеру
     */
    public void compact() {
        if (!checked) {
            prepare();
        }
        boolean checked = false;
        int check = 0;
        int currentPos = -1; // размер для стартвой позиции
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

    /**
    * упорядочивание списка блоков по размеру и изменения статуса "перемещён" у каждого блока
     */
    private void prepare() {
        blocks = (ArrayList<Block>) blocks.stream().sorted().collect(Collectors.toList()); // сортировка
        blocks.forEach(x -> x.setMoved(false)); // изменение параметра "перемещён"
        Collections.reverse(blocks); // сортировка "справа налево"
    }

    /**
     * Сжатие памяти путём перезаписывания блока памяти в начало и удаления его из кона памяти
     * @param thisPosition (конкретная позиция)
     * @param size (размер блока)
     */
    private void compress(int thisPosition, int size) {
        // сохранение блока памяти с соответствующим размером (size)
        ArrayList<Block> changedBlocks = (ArrayList<Block>) blocks.stream().filter(x -> x.getSize() <= size && !x.isMoved() && x.getPosition() > thisPosition).limit(1).collect(Collectors.toList());
        if (changedBlocks.size() != 0) { // если такой блок вообще есть
            this.add(thisPosition, changedBlocks.get(0).getSize()); // заполняем им свободный промежуток памяти
            this.delete(changedBlocks.get(0)); // удаляем дупликат из конца памяти
            changedBlocks.get(0).setMoved(true); // помечаем блок статусом "смещён"
        }
    }

    /**
     * добавление блока в память по определённому адресу
     * @param currentPos (конкретная позиция начала блока)
     * @param size (размер блока)
     */
    private void add(int currentPos, int size) {
        bytes[currentPos] = startBlock;
        for (int i = currentPos + 1; i < currentPos + size; i++) {
            bytes[i] = point;
        }
    }

    /**
     * освобождение памяти от блока
     * @param block (блок на удаление)
     */
    private void delete(Block block) {
        for (int i = block.getPosition(); i < block.getPosition() + block.getSize(); i++) {
            bytes[i] = 0;
        }
    }

    /**
     * помещение блока в наиболее правую позицию
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

    /**
     * обновление состояния всей памяти (курсора свободной памяти, статусов "компактизировано" и "без дыр".
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
