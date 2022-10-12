package arina.euler;

public class Friends {
    private long i;
    private long j;

    public Friends(long i, long j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return this.i + " - " + this.j;
    }

}
