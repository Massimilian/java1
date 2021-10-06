public class SimpleApplication {
    public void print() throws InterruptedException {
        System.out.println("Print worked");
        Thread.sleep(1);
    }

    public void foo(String text) throws InterruptedException {
        System.out.println(text);
        Thread.sleep(10);
    }

    public String bar() {
        return "bar";
    }

    public String runAll(String text) throws InterruptedException {
        Thread.sleep(10);
        this.print();
        this.foo(text);
        String result = this.bar();
        System.out.println("runAll worked!");
        return result;
    }
    public static void main(String[] args) throws InterruptedException {
        SimpleApplication sa = new SimpleApplication();
        System.out.println("SimpleApplication: main стартовал");
        sa.runAll("test");
    }
}
