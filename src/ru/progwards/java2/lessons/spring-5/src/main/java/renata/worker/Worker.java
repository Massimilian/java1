package renata.worker;

public abstract class Worker {
    private int id;
    private String name;
    private double forPay;

    public Worker(int id, String name, double forPay) {
        this.id = id;
        this.name = name;
        this.forPay = forPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getForPay() {
        return forPay;
    }

    public void setForPay(double forPay) {
        this.forPay = forPay;
    }

    public abstract double count();

    @Override
    public String toString() {
        return this.getId() + "@" + this.getName() + "@" + this.getForPay() + "@" + this.getClass().getSimpleName() + "@";
    }
}