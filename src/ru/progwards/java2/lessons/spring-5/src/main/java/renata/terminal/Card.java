package renata.terminal;

import java.time.LocalDateTime;

public abstract class Card {
    private static int num = 0;
    int month = LocalDateTime.now().getMonth().getValue();
    boolean isBlocked = false;
    private int number;
    private int numberOfWays;
    private int remainedWays;
    private double sum = 0;
    private double mouthPay;
    private double priceOfOneWay;

    public Card(double mouthPay, double priceOfOneWay, int numberOfWays) {
        this.setNumber(Card.getNum());
        this.setMouthPay(mouthPay);
        this.setSum(0);
        this.setPriceOfOneWay(priceOfOneWay);
        this.setNumberOfWays(numberOfWays);
        this.setRemainedWays(numberOfWays);
    }

    public static int getNum() {
        num++;
        return num;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getMouthPay() {
        return mouthPay;
    }

    public void setMouthPay(double mouthPay) {
        this.mouthPay = mouthPay;
    }

    public double getPriceOfOneWay() {
        return priceOfOneWay;
    }

    public void setPriceOfOneWay(double priceOfOneWay) {
        this.priceOfOneWay = priceOfOneWay;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getNumberOfWays() {
        return numberOfWays;
    }

    public void setNumberOfWays(int numberOfWays) {
        this.numberOfWays = numberOfWays;
    }

    public int getRemainedWays() {
        return remainedWays;
    }

    public void setRemainedWays(int remainedWays) {
        this.remainedWays = remainedWays;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    public void checkMonth() {
        if (!isBlocked) {
            if (LocalDateTime.now().getMonth().getValue() != this.month) {
                this.month = LocalDateTime.now().getMonth().getValue();
                this.sum -= this.mouthPay;
                this.setRemainedWays(numberOfWays);
                if (this.sum < 0) {
                    this.isBlocked = true;
                }
            }
        }
    }
}
