package diningPhilosopher;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final ReentrantLock lock = new ReentrantLock();

    public boolean take() {
        return lock.tryLock();
    }

    public void put() {
        lock.unlock();
    }

    public void putIfBusy() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
