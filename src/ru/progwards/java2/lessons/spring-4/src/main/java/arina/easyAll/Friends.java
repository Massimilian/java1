package arina.easyAll;

import java.util.Objects;

public class Friends {
    private int i;
    private int j;

    public Friends(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return this.i + " - " + this.j;
    }

    public boolean reallyFriends() {
        int[] iDiv = this.findDivisions(this.i);
        int[] jDiv = this.findDivisions(this.j);
        return i == this.findSum(jDiv) && j == this.findSum(iDiv) && i != j;
    }

    private int findSum(int[] div) {
        int result = 0;
        for (int k = 0; k < div.length; k++) {
            result += div[k];
        }
        return result;
    }

    private int[] findDivisions(int value) {
        int[] divisions = new int[10000];
        int count = 0;
        for (int i = 1; i <= value / 2; i++) {
            if (value % i == 0) {
                divisions[count++] = i;
            }
        }
        int[] result = new int[count];
        for (int k = 0; k < count; k++) {
            result[k] = divisions[k];
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friends friends = (Friends) o;
        return (i == friends.i && j == friends.j) || (j == friends.i && i == friends.j);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
