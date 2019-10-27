package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

import java.time.LocalDateTime;

public class Transaction implements Comparable<Transaction> {
    private String cardName;
    private LocalDateTime time;
    private PaymentInfo paymentInfo;

    public Transaction(String cardName, PaymentInfo paymentInfo) {
        this.cardName = cardName;
        this.time = LocalDateTime.now();
        this.paymentInfo = paymentInfo;
    }

    public String getCardName() {
        return cardName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.time.compareTo(o.getTime());
    }
}
