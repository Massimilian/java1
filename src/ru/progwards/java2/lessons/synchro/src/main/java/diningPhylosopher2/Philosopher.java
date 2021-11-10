package diningPhylosopher2;

public class Philosopher {
    private final String name;
    private Fork right;
    private Fork left;
    private long reflectTime;
    private long eatTime;
    private long reflectSum;
    private long eatSum;
    private boolean finish = false;

    public Philosopher(String name, long reflectTime, long eatTime) {
        this.name = name;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
    }

    public void setFinish() {
        this.finish = true;
    }

    public void getForks(Fork right, Fork left) {
        this.right = right;
        this.left = left;
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
            if (Simposion.currentPosition == right.getNum()) {
                if (left.take()) {
                    right.take();
                    Simposion.changePosition();
                    Thread thread = new Thread(new Action(name, "is eating..."));
                    thread.start();
                    try {
                        Thread.sleep(reflectTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                    right.put();
                    left.put();
                    thread.interrupt();
                    eatSum += eatTime;
                    this.reflect();
                } else {
                    reflect();
                }
            }
            reflect();
        }
    }

    public void printInfo() {
        System.out.printf("Philosofer %s eated %d and thought %d ms.%s", this.name, this.eatSum, this.reflectSum, System.lineSeparator());
    }
}

