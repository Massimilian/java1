package renata.worker;

public class MonthWorker extends Worker{
    public MonthWorker(int id, String name, double forPay) {
        super(id, name, forPay);
    }

    @Override
    public double count() {
        return this.getForPay();
    }
}
