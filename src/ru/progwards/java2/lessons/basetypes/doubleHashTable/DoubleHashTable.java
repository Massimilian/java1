package ru.progwards.java2.lessons.basetypes.doubleHashTable;

import java.util.Iterator;

public class DoubleHashTable<T> {
    private int attemps = 0;
    private DoubleHashTableObject<T>[] hash = new DoubleHashTableObject[101];
    private int size = hash.length;


    public String add(DoubleHashTableObject dhto) {
        return this.add(dhto, this.hash);
    }

    public String remove(int key) {
        String result;
        if (hash[key % size] != null) {
            hash[key % size] = null;
            result = String.format("Value with key №%d removed form position №%d.", key, key % size);
        } else {
            result = "Cannot do this operation: the value was not found.";
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public void change(int key1, int key2) {
        DoubleHashTableObject dhto = new DoubleHashTableObject(this.get(key1));
        dhto.setKey(key2);
        this.remove(key1);
        this.add(dhto);
    }

    public Iterator<DoubleHashTable<T>> getIterator() {
        Iterator<DoubleHashTable<T>> iterator = new Iterator() {
            private int real = 0;
            private DoubleHashTableObject<T>[] array = this.prepare();
            private int count = 0;

            @Override
            public boolean hasNext() {
                return real > count;
            }

            @Override
            public T next() {
                return array[count++].getValue();
            }

            private DoubleHashTableObject<T>[] prepare() {
                DoubleHashTableObject<T>[] arr = new DoubleHashTableObject[size];
                int count = 0;
                for (int i = 0; i < size; i++) {
                    if (hash[i] != null) {
                        arr[count++] = hash[i];
                    }
                }
                real = count;
                return arr;
            }
        };
        return iterator;

    }

    private String add(DoubleHashTableObject dhto, DoubleHashTableObject[] actual) {
        String result = String.format("Value added. Old key (%d) saved.", dhto.getKey());
        int key = dhto.getKey() % actual.length;
        if (actual[key] == null) {
            actual[key] = dhto;
            result = attemps > 0 ? String.format("Value added. New key (%d) saved.", dhto.getKey()) : result;
            attemps = 0;
        } else {
            attemps++;
            dhto.setKey(dhto.getKey() << 1);
            if (attemps == 10) {
                this.rebuild();
            }
            result = this.add(dhto);
        }
        this.size = this.size == hash.length ? this.size : hash.length;
        return result;
    }

    public T get(int key) {
        return (T) hash[key % hash.length].getValue();
    }

    private int getLength(int size) {
        boolean isSimple = false;
        int[] pluses = {4, 2, 4, 2, 4, 6, 2, 6};
        size = size << 1;
        while (!isSimple) {
            size++;
            if (size % 2 != 0 && size % 3 != 0 && size % 5 != 5) {
                int order = 0;
                int value = 7;
                while (size % value != 0 && value <= Math.sqrt(size)) {
                    value += pluses[order++ % pluses.length];
                    isSimple = value > Math.sqrt(size);
                }
            }
        }
        return size;
    }

    private void rebuild() {
        DoubleHashTableObject<T>[] temp = new DoubleHashTableObject[getLength(hash.length)];
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != null) {
                DoubleHashTableObject dhto = hash[i];
                this.add(dhto, temp);
            }
        }
        hash = temp;
    }
}
