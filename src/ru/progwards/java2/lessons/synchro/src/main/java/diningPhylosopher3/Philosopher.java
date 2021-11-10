package diningPhylosopher3;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher {
    private String name;
    private Philosopher left;
    private Philosopher right;
    private long reflectTime;
    private long eatTime;
    private long reflectSum;
    private long eatSum;
    private boolean finish = false;
    private ReentrantLock lock = new ReentrantLock();

    public Philosopher(String name, long reflectTime, long eatTime) {
        this.name = name;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
    }

    public void setNeighbour(Philosopher left, Philosopher right) {
        this.left = left;
        this.right = right;
    }

    public void setFinish() {
        this.finish = true;
    }

    void reflect() {
        if (!finish) {
            Thread thread = new Thread(new Action(name, "is thinking..."));
            thread.start();
            try {
                Thread.sleep(reflectTime);
            } catch (InterruptedException e) {
                return;
            }
            thread.interrupt();
            reflectSum += reflectTime;
            this.eat();
        }
    }

    void eat() {
        if (!finish) {
            if (this.isFree()) {
                Thread thread = new Thread(new Action(name, "is eating..."));
                thread.start();
                try {
                    Thread.sleep(reflectTime);
                } catch (InterruptedException e) {
                    return;
                }
                thread.interrupt();
                this.lock.unlock();
                eatSum += eatTime;
            }
            this.reflect();
        }
    }

    private boolean isFree() {
        boolean isFree;
        synchronized (right) {
            synchronized (left) {
                isFree = !right.lock.isLocked() && !left.lock.isLocked();
                if (isFree) {
                    lock.lock();
                }
            }
        }
        return isFree;
    }

    public void unlocker() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public void printInfo() {
        System.out.printf("Philosofer %s eated %d and thought %d ms.%s", this.name, this.eatSum, this.reflectSum, System.lineSeparator());
    }

}

