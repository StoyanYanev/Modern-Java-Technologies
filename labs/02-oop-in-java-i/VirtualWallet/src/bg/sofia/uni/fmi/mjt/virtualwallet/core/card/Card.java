package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {
    private String name;
    private double amount;

    public Card(String name) {
        this.name = name;
        amount = 0;
    }

    public abstract boolean executePayment(double cost);

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    protected boolean setAmount(double amount) {
        if (isNegativeAmount(amount)) {
            return false;
        }
        this.amount = amount;

        return true;
    }

    public boolean deposit(double amount) {
        if (isNegativeAmount(amount)) {
            return false;
        }
        this.amount += amount;

        return true;
    }

    private boolean isNegativeAmount(double amount) {
        return amount < 0;
    }
}
