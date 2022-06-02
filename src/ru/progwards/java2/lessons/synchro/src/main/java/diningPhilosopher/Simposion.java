package diningPhilosopher;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

// принцип работы - пытаемся взять сразу две вилки, если не получается - бросаем всё и размышляем.
public class Simposion {
    final int number = 5;
    private final Philosopher[] philosophers;
    private final Fork[] forks;
    private final ReentrantLock waiter = new ReentrantLock();
    private final String[] names = {"Dekart", "Aristotel", "Kant", "Platon", "Confutsi", "Um", "Sokrat", "Makiavelli", "Lock", "Diogen", "Spinosa", "Russo"};
    private Thread[] threads = new Thread[number];

    public Simposion(long reflectTime, long eatTime) {
        this.forks = new Fork[number];
        this.philosophers = new Philosopher[number];
        for (int i = 0; i < number; i++) {
            forks[i] = new Fork();
        }
        makePhilosofers(reflectTime, eatTime);
        serve();
    }

    private void serve() {
        for (int i = 0; i < number - 1; i++) {
            philosophers[i].getForks(forks[i], forks[i + 1]);
        }
        philosophers[number - 1].getForks(forks[number - 1], forks[0]);
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
            philosophers[count++] = new Philosopher(iterator.next(), reflectTime, eatTime, waiter);
        }
    }

    public void start() {
        for (int i = 0; i < number; i++) {
            final int fin = i; // Runnable требует финальную переменную
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

        public void print () {
            for (int i = 0; i < number; i++) {
                philosophers[i].printInfo();
            }
        }

        public static void main (String[]args) throws InterruptedException {
            Simposion simposion = new Simposion(500, 500);
            simposion.start();
            Thread.sleep(5000);
            simposion.stop();
            simposion.print();
        }
    }
