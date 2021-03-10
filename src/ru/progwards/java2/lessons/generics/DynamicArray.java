package ru.progwards.java2.lessons.generics;

/**
 * Class of dynamic array
 *
 * @param <T> type of parameter
 */
public class DynamicArray<T> {
    private T[] array = (T[]) new Object[1];
    private int size = 1;
    private int position = 0;

    public DynamicArray() {
    }

    /**
     * Method for add new value
     *
     * @param t is a value
     * @return true
     */
    public boolean add(T t) {
        checkSize();
        array[position++] = t;
        return true;
    }

    /**
     * Method for check a new size
     */
    private void checkSize() {
        if (this.size == this.position) {
            size <<= 1;
            this.array = createNewArray();
        }
    }

    /**
     * Method for insert a new value
     *
     * @param pos is a place to insert
     * @param t   is a value to insert
     * @return is inserted
     */
    public boolean insert(int pos, T t) {
        boolean success = false;
        if (pos == this.position) {
            success = this.add(t);
        } else if (pos < this.position) {
            checkSize();
            T[] temp = (T[]) new Object[size];
            System.arraycopy(this.array, 0, temp, 0, pos);
            temp[pos] = t;
            System.arraycopy(this.array, pos, temp, pos + 1, array.length - 1 - pos);
            this.array = temp;
            position++;
            success = true;
        } else if (pos > this.position) {
            System.out.println("Incorrect position");
        }
        return success;
    }

    /**
     * Method to remove the value
     *
     * @param pos position for remove
     * @return the value
     */
    public T remove(int pos) {
        T t = array[pos];
        array[pos] = null;
        return t;
    }

    /**
     * Method for create a new array
     *
     * @return a new filled array
     */
    private T[] createNewArray() {
        T[] temp = (T[]) new Object[size];
        System.arraycopy(this.array, 0, temp, 0, array.length);
        return temp;
    }

    /**
     * Method for get the value
     *
     * @param pos is a position for get
     * @return value
     */
    public T get(int pos) {
        T result = null;
        if (pos < this.position) {
            result = array[pos];
        } else {
            System.out.println("Impossible position");
        }
        return result;
    }

    /**
     * Method for get a real size of array
     *
     * @return size
     */
    public int size() {
        int result = 0;
        for (T t : array) {
            result = t != null ? result + 1 : result;
        }
        return result;
    }

    public static void main(String[] args) {
        DynamicArray<String> ds = new DynamicArray<>();
        ds.add("two");
        ds.add("three");
        ds.insert(0, "zero");
        ds.insert(1, "one");
        ds.insert(100, "impossible number");
        ds.insert(4, "four");
        System.out.println(ds.remove(0));
        DynamicArray<Integer> dsInt = new DynamicArray<>();
        dsInt.add(1);
        dsInt.add(2);
        dsInt.insert(0, 0);
        dsInt.remove(1);
        System.out.println(dsInt.size());
    }
}
