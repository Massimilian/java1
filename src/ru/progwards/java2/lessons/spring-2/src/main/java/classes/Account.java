package classes;

public class Account {
    private int id;
    private String holder;
    private int amount;

    public void setId(int id) {
        this.id = id;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getHolder() {
        return holder;
    }

    public int getAmount() {
        return amount;
    }
}
