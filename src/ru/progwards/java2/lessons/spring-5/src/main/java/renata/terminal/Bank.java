package renata.terminal;

public abstract class Bank {
    public void fillCard(Card card, double money) {
        card.setSum(card.getSum() + money);
        System.out.println("Your card has been filled.");
        this.getBalance(card);
    }

    public void getBalance(Card card) {
        System.out.println("Balance: " + card.getSum());
    }
}
