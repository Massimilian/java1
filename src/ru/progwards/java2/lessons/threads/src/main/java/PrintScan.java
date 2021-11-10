import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintScan {
    static void print(String name, int pages) {
        synchronized (name) {
            doWork("print", name, pages);
        }
    }

    static void scan(String name, int pages) {
        synchronized (name) {
            doWork("scan", name, pages);
        }
    }

    private static void doWork(String action, String name, int pages) {
        for (int i = 0; i < pages; ) {
            System.out.printf("%s <%s> page %d%s", action, name, ++i, System.lineSeparator());
            try {
                Thread.sleep(action.equals("print") ? 50 : 70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService print = Executors.newFixedThreadPool(10);
        ExecutorService scan = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            print.submit(new Printer("Name - " + i, 10));
            scan.submit(new Scanner("Name - " + i, 10));
        }
        print.shutdown();
        scan.shutdown();
    }
}

class Printer implements Runnable {
    private final String name;
    private final int pages;

    public Printer(String name, int pages) {
        this.name = name;
        this.pages = pages;
    }

    public void run() {
        PrintScan.print(name, pages);
    }
}

class Scanner implements Runnable {
    private final String name;
    private final int pages;

    public Scanner(String name, int pages) {
        this.name = name;
        this.pages = pages;
    }

    public void run() {
        PrintScan.scan(name, pages);
    }
}