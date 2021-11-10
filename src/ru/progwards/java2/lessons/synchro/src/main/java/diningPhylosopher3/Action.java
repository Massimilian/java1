package diningPhylosopher3;

public class Action implements Runnable {
    private String action;

    public Action(String name, String action) {
        this.action = name + " " + action;
    }

    public void run() {
        System.out.println(this.action);
        try {
            Thread.sleep(500);
            System.out.println(this.action);
        } catch (InterruptedException e) {
            return;
        }
    }
}

