package ru.progwards.java1.lessons.arrays;

import org.junit.Assert;

import static org.hamcrest.core.Is.is;

public class DIntArray {
    private int[] array = new int[0];

    /**
     * Adding a new value to the increasing array
     *
     * @param num (for add)
     */
    public void add(int num) {
        int[] newArr = new int[this.array.length + 1];
        System.arraycopy(this.array, 0, newArr, 0, this.array.length);
        newArr[array.length] = num;
        this.array = newArr;
    }

    /**
     * Adding new element into the particular position and increasing array
     *
     * @param pos (position)
     * @param num (for add)
     */
    public void atInsert(int pos, int num) {
        if (isRightPosition(pos)) {
            int[] newArr = new int[this.array.length + 1];
            System.arraycopy(this.array, 0, newArr, 0, pos);
            System.arraycopy(this.array, pos, newArr, pos + 1, this.array.length - 1);
            newArr[pos] = num;
            this.array = newArr;
        }
    }

    /**
     * Deleting from the particular position
     *
     * @param pos (position)
     */
    public void atDelete(int pos) {
        if (isRightPosition(pos)) {
            int[] newArr = new int[this.array.length - 1];
            if (pos == this.array.length - 1) {
                System.arraycopy(this.array, 0, newArr, 0, newArr.length);
            } else {
                System.arraycopy(this.array, 0, newArr, 0, pos);
                System.arraycopy(this.array, pos + 1, newArr, pos, this.array.length - 1);
            }
            this.array = newArr;
        }
    }

    /**
     * getting position and returning the value of that position
     *
     * @param pos (position)
     * @return (value)
     */
    public int at(int pos) {
        int result = 0;
        if (isRightPosition(pos)) {
            result = this.array[pos];
        }
        return result;
    }

    /**
     * Checking the posibility of position
     *
     * @param pos (position)
     * @return (posibility)
     */
    private boolean isRightPosition(int pos) {
        boolean result = true;
        if (pos < 0 || pos >= this.array.length) {
            System.out.println("Impossible operation");
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        DIntArray dia = new DIntArray();
        dia.add(1);
        dia.add(3);
        Assert.assertThat(dia.at(0), is(1));
        Assert.assertEquals(3, dia.at(1));
        dia.atInsert(1, 2);
        Assert.assertEquals(2, dia.at(1));
        dia.atDelete(2);
        dia.atDelete(0);
        Assert.assertEquals(0, dia.at(1));
        Assert.assertThat(dia.at(0), is(2));
    }
}
