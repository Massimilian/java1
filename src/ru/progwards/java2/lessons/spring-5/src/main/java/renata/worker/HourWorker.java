package renata.worker;

public class HourWorker extends Worker{
    public HourWorker(int id, String name, double forPay) {
        super(id, name, forPay);
    }

    @Override
    public double count() {
        return 20.8 * 8 * this.getForPay();
    }
}
