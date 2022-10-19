package renata.terminal;

import java.util.Scanner;

public class Terminal {
    private int numberOfPasses = 0;
    private String statistics = "";
    private double moneyStatistic = 0;
    private double schoolMouthPay;
    private double studMouthPay;
    private double socialMouthPay;
    private double schoolOneWay;
    private double studOneWay;
    private double socialOneWay;
    private int schoolNumberOfWays;
    private int studNumberOfWays;
    private int socialNumberOfWays;

    public Terminal() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter mouth sum for school card:");
//        this.schoolMouthPay = scanner.nextDouble();
//        System.out.println("Enter mouth sum for student card:");
//        this.studMouthPay = scanner.nextDouble();
//        System.out.println("Enter mouth sum for social card:");
//        this.socialMouthPay = scanner.nextDouble();
//        System.out.println("Enter the one-way price for school card:");
//        this.schoolOneWay = scanner.nextDouble();
//        System.out.println("Enter the one-way price for student card:");
//        this.studOneWay = scanner.nextDouble();
//        System.out.println("Enter the one-way price for social card:");
//        this.socialOneWay = scanner.nextDouble();
//        System.out.println("Enter number of free ways for school card:");
//        this.schoolNumberOfWays = scanner.nextInt();
//        System.out.println("Enter number of free ways for student card:");
//        this.studNumberOfWays = scanner.nextInt();
//        System.out.println("Enter number of free ways for social card:");
//        this.socialNumberOfWays = scanner.nextInt();
        this.schoolMouthPay = 100;
        this.studMouthPay = 500;
        this.socialMouthPay = 0;
        this.schoolOneWay = 50;
        this.studOneWay = 100;
        this.socialOneWay = 100;
        this.schoolNumberOfWays = 60;
        this.studNumberOfWays = 30;
        this.socialNumberOfWays = -1;
    }


    public Card createNewCard() {
        System.out.println("Enter your card type (school, student, social):");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        boolean isNotChecked = true;
        Card card = null;
        while (isNotChecked) {
            switch (s) {
                case "school":
                    isNotChecked = false;
                    card = new SchoolCard(schoolMouthPay, schoolOneWay, schoolNumberOfWays);
                    break;
                case "student":
                    isNotChecked = false;
                    card = new StudentCard(studMouthPay, studOneWay, studNumberOfWays);
                    break;
                case "social":
                    isNotChecked = false;
                    card = new SocialCard(socialMouthPay, socialOneWay, socialNumberOfWays);
                    break;
                default:
                    break;
            }
        }
        return card;
    }

    public void pass(Card card) {
        String statistics = "";
        card.checkMonth();
        if (!card.isBlocked()) {
            statistics += "Card №:" + card.getNumber() + ", type '" + card.getType() + "'";
            if (card.getRemainedWays() != 0) {
                card.setRemainedWays(card.getRemainedWays() - 1);
                System.out.println("You may pass; paid by remaining passes.");
                statistics += " paid by remaining passes.";
            } else {
                if (card.getSum() >= card.getPriceOfOneWay()) {
                    card.setSum(card.getSum() - card.getPriceOfOneWay());
                    System.out.println("You may pass; paid by card money.");
                    statistics += " paid by remained money, sum was " + card.getPriceOfOneWay();
                    this.moneyStatistic += card.getPriceOfOneWay();
                } else {
                    System.out.println("Insifficient funds.");
                    statistics += " tried to pay.";
                    this.numberOfPasses--;
                }
            }
            this.statistics += statistics + System.lineSeparator();
            this.numberOfPasses++;
        } else {
            System.out.println("Your card has been blocked");
        }
    }

    public String getStatistics() {
        String result = this.statistics + System.lineSeparator() + "---" +
                System.lineSeparator() + "All cash: " + this.moneyStatistic + System.lineSeparator() +
                "---" + System.lineSeparator() + "Number of passengers " + this.numberOfPasses;
        this.statistics = "";
        this.moneyStatistic = 0;
        this.numberOfPasses = 0;
        return result;
    }
}
