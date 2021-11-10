package diningPhylosopher2;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    final int num;
    private ReentrantLock lock = new ReentrantLock();

    public Fork(int num) {
        this.num = num;
    }

    public boolean take() {
        return lock.tryLock();
    }

    public void put() {
        lock.unlock();
    }

    public int getNum() {
        return num;
    }
}
