package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;

/**
 * Special class for keep different types of data
 *
 * @param <K> key
 * @param <V> value
 */
public class DoubleHashTable<K extends HashValue, V> implements Iterable<V> {
    int size = 10;
    int attempts = size / 10;
    Bean<K, V>[] beans;

    public DoubleHashTable() {
        this.beans = new Bean[size];
    }

    /**
     * Method for add new key & value
     *
     * @param key   is a key for get value
     * @param value for add
     */
    public void add(K key, V value) {
        int attempt = 0;
        int position = key.getHash() % this.size;
        while (this.beans[position] != null && this.beans[position].getValue() != null && attempt < attempts) {
            attempt++;
            position = incrPosition(position);
        }
        if (attempt >= attempts) {
            rebuild();
            add(key, value);
        } else {
            beans[position] = new Bean(key, value);
        }
    }

    /**
     * Method for get value by key
     *
     * @param key for find the value
     * @return value from key
     */
    public V get(K key) {
        V v = null;
        int position = findCorrectPosition(key);
        Bean<K, V> bean = beans[position];
        if (bean != null) {
            v = bean.getValue();
        }
        return v;
    }

    /**
     * Method for delete value
     *
     * @param key for find the value
     */
    public void remove(K key) {
        int position = findCorrectPosition(key);
        beans[position] = new Bean<>(key, null);
    }

    /**
     * T\Method for change key of value
     *
     * @param key1 for delete
     * @param key2 for put
     */
    public void change(K key1, K key2) {
        int position = findCorrectPosition(key1);
        V v = beans[position].getValue();
        this.remove(key1);
        this.add((K) key2, v);
    }

    /**
     * Method for get the real size
     *
     * @return number of beans
     */
    public int size() {
        int result = 0;
        for (Bean<K, V> bean : beans) {
            if (bean != null && bean.getValue() != null) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {
            int position = -1;

            @Override
            public boolean hasNext() {
                int tempPos = position + 1;
                while (tempPos < size && (beans[tempPos] == null || beans[tempPos].getValue() == null)) {
                    tempPos++;
                }
                return tempPos < size;
            }

            @Override
            public V next() {
                V v = null;
                position++;
                while (position < size) {
                    if (beans[position] == null || beans[position].getValue() == null) {
                        position++;
                    } else {
                        v = beans[position].getValue();
                        break;
                    }
                }
                return v;
            }
        };
    }

    /**
     * Method for find the correct position of value by key
     *
     * @param key for find the position in array
     * @return number of position
     */
    private int findCorrectPosition(K key) {
        int position = key.getHash() % this.size;
        Bean<K, V> bean = beans[position];
        while (bean != null && !(bean.getKey().getClass().getSimpleName().equals(key.getClass().getSimpleName())
                && bean.getKey().equals(key))) {
            position = incrPosition(position);
            bean = beans[position];
        }
        return position;
    }

    /**
     * Method for increment position for find the correct place
     *
     * @param position for incrementation
     * @return incremented position
     */
    private int incrPosition(int position) {
        return position == this.size - 1 ? size % position : ++position;
    }

    /**
     * Rebuild the array
     */
    private void rebuild() {
        nextBig();
        rewrite();
    }

    /**
     * Method for rewrite the array
     */
    private void rewrite() {
        Bean<K, V>[] oldValue = this.beans;
        this.beans = new Bean[this.size];
        for (int i = 0; i < oldValue.length; i++) {
            if (oldValue[i] != null && oldValue[i].getValue() != null) {
                this.add((K) oldValue[i].getKey(), oldValue[i].getValue());
            }
        }
    }

    /**
     * Method for find the next size of array
     */
    private void nextBig() {
        this.size *= 2;
        while (isNotSimple(this.size)) {
            this.size++;
        }
        this.attempts = size / 10;
    }

    /**
     * Method for find the next size number
     *
     * @param size is a previous size
     * @return new bigger size
     */
    private boolean isNotSimple(int size) {
        boolean result = false;
        for (int i = 2; i < Math.sqrt(size); i++) {
            if (size % i == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DoubleHashTable<HashValue, String> dht = new DoubleHashTable<>();
        dht.add(new StringKey("one"), "One");
        dht.add(new StringKey("two"), "Two");
        assert dht.size() == 2;
        dht.add(new StringKey("three"), "Three");
        assert dht.size() == 3;
        dht.remove(new StringKey("three"));
        assert dht.size() == 2;
        assert dht.get(new StringKey("one")).equals("One");
        assert dht.get(new StringKey("two")).equals("Two");
        assert dht.get(new StringKey("three")) == null;
        dht.remove(new StringKey("two"));
        dht.add(new IntKey(110182), "Один");
        Iterator<String> iterator = dht.iterator();
        assert iterator.next().equals("One");
        assert iterator.hasNext() == true;
        assert iterator.next().equals("Один");
        assert iterator.hasNext() == false;
        assert iterator.next() == null;
    }

}
