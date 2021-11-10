package diningPhilosopher;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher {
    private ReentrantLock waiter;
    private String name;
    private Fork right;
    private Fork left;
    private long reflectTime;
    private long eatTime;
    private long reflectSum;
    private long eatSum;
    private boolean finish = false;
    private boolean isHungry = true;

    public Philosopher(String name, long reflectTime, long eatTime, ReentrantLock waiter) {
        this.name = name;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
        this.waiter = waiter;
    }

    public void getForks(Fork right, Fork left) {
        this.right = right;
        this.left = left;
    }

    public void setFinish() {
        this.finish = true;
    }

    void reflect() {
        if (!finish) {
            Thread thread = new Thread(new Action(name, "is thinking...", isHungry));
            thread.start();
            try {
//                Thread.sleep(isHungry? reflectTime: reflectTime / 4);
                Thread.sleep(reflectTime);
            } catch (InterruptedException e) {
                return;
            }
            thread.interrupt();
            reflectSum += reflectTime;
//            reflectSum += isHungry? reflectTime : reflectTime / 4;
            isHungry = true;
            this.eat();
        }
    }

    void eat() {
        if (!finish) {
            if (waiter.tryLock()) {
                if (right.take() && left.take()) {
                    waiter.unlock();
                    Thread thread = new Thread(new Action(name, "is eating.", false));
                    thread.start();
                    try {
                        Thread.sleep(eatTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                    thread.interrupt();
                    right.put();
                    left.put();
                    eatSum += eatTime;
                    isHungry = false;
                } else {
                    right.putIfBusy();
                    left.putIfBusy();
                    waiter.unlock();
                }
            }
            this.reflect();
        }
    }

    public void printInfo() {
        System.out.printf("Philosofer %s eated %d and thought %d ms.%s", this.name, this.eatSum, this.reflectSum, System.lineSeparator());
    }

}

