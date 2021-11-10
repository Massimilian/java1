package diningPhylosopher3;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Simposion {
    private final int number = 5;
    private Philosopher[] philosophers;
    private final String[] names = {"Dekart", "Aristotel", "Kant", "Platon", "Confutsi", "Um", "Sokrat", "Makiavelli", "Lock", "Diogen", "Spinosa", "Russo"};
    private Thread[] threads = new Thread[number];

    public Simposion(long reflectTime, long eatTime) {
        this.philosophers = new Philosopher[number];
        makePhilosofers(reflectTime, eatTime);
        setNeighbours();
    }

    private void setNeighbours() {
        for (int i = 0; i < number; i++) {
            setNeighbour(i);
        }
    }

    private void setNeighbour(int i) {
        philosophers[i].setNeighbour(philosophers[changeI(i, false)], philosophers[changeI(i, true)]);
    }

    private int changeI(int i, boolean isPlus) {
        if (isPlus) {
            return i == number - 1 ? 0 : ++i;
        } else {
            return i == 0 ? number - 1 : --i;
        }
    }

    private void makePhilosofers(long reflectTime, long eatTime) {
        HashSet<String> temp = new HashSet<>();
        Random random = new Random();
        while (temp.size() < number) {
            temp.add(names[random.nextInt(names.length)]);
        }
        Iterator<String> iterator = temp.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            philosophers[count++] = new Philosopher(iterator.next(), reflectTime, eatTime);
        }
    }

    public void start() {
        for (int i = 0; i < number; i++) {
            final int fin = i;
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    philosophers[fin].eat();
                }
            });
            threads[i].start();
        }
    }

    public void stop() throws InterruptedException {
        for (int i = 0; i < number; i++) {
            philosophers[i].setFinish();
        }
        for (int i = 0; i < number; i++) {
            threads[i].join();
        }
    }

    public void print() {
        for (int i = 0; i < number; i++) {
            philosophers[i].printInfo();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Simposion simposion = new Simposion(500, 500);
        simposion.start();
        Thread.sleep(5000);
        simposion.stop();
        simposion.print();
    }
}
