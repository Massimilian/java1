package diningPhilosopher;

public class Action implements Runnable {
    private String action;
    private boolean shortTimed;

    public Action(String name, String action, boolean shortTimed) {
        this.action = name + " " + action;
        this.shortTimed = shortTimed;
    }

    public void run() {
        System.out.println(this.action);
        do {
            try {
                Thread.sleep(500);
                System.out.println(this.action);
            } catch (InterruptedException e) {
                break;
            }
        } while (!shortTimed);
    }
}

