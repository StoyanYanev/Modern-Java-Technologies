package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class StandardCard extends Card {
    public StandardCard(String name) {
        super(name);
    }

    @Override
    public boolean executePayment(double cost) {
        if (cost < 0) {
            return false;
        }
        double newAmount = this.getAmount() - cost;

        return this.setAmount(newAmount);
    }
}
