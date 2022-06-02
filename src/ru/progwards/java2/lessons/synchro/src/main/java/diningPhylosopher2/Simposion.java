package diningPhylosopher2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
// Круговая смена виолок у философов - когда мы берём две вилки, поток сдвигается на два философа "по часовой стрелке" и затем повторяет операцию. НЕ РАБОТАЕТ на чётном количестве философов
public class Simposion {
    static final int number = 4;
    public static int currentPosition = 0;
    private final Philosopher[] philosophers;
    private final Fork[] forks;
    final String[] names = {"Dekart", "Aristotel", "Kant", "Platon", "Confutsi", "Um", "Sokrat", "Makiavelli", "Lock", "Diogen", "Spinosa", "Russo"};
    Thread[] threads = new Thread[number];

    public Simposion(long reflectTime, long eatTime) {
        this.forks = new Fork[number];
        this.philosophers = new Philosopher[number];
        for (int i = 0; i < number; i++) {
            forks[i] = new Fork(i);
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

    public static void changePosition() {
        plus();
        plus();
    }

    private static void plus() {
        currentPosition++;
        currentPosition = currentPosition == number? 0 : currentPosition;
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
