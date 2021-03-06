package bg.sofia.uni.fmi.mjt.virtualwallet.core;

import bg.sofia.uni.fmi.mjt.virtualwallet.core.card.Card;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.PaymentInfo;
import bg.sofia.uni.fmi.mjt.virtualwallet.core.payment.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class VirtualWallet implements VirtualWalletAPI {
    private static final int MAX_NUMBER_OF_CARDS = 5;
    private static final int MAX_NUMBER_OF_TRANSACTIONS = 10;

    private Map<String, Card> cards;
    private Queue<Transaction> transactions;

    public VirtualWallet() {
        cards = new HashMap<>();
        transactions = new PriorityQueue<>();
    }

    @Override
    public boolean registerCard(Card card) {
        if (!isCardValid(card)) {
            return false;
        }
        if (!canCardBeRegistrated(card)) {
            return false;
        }
        cards.put(card.getName(), card);

        return true;
    }

    @Override
    public boolean executePayment(Card card, PaymentInfo paymentInfo) {
        if (!isCardValid(card) || paymentInfo == null || !isCardExisting(card)) {
            return false;
        }
        Card currentCard = cards.get(card.getName());
        boolean isPaymentSuccessful = currentCard.executePayment(paymentInfo.getCost());
        if (isPaymentSuccessful) {
            Transaction currentTransaction = new Transaction(card.getName(), paymentInfo);
            addToTransactionsHistory(currentTransaction);
        }

        return isPaymentSuccessful;
    }

    @Override
    public boolean feed(Card card, double amount) {
        if (!isCardValid(card) || !isCardExisting(card)) {
            return false;
        }
        Card currentCard = cards.get(card.getName());

        return currentCard.deposit(amount);
    }

    @Override
    public Card getCardByName(String name) {
        return cards.get(name);
    }

    @Override
    public int getTotalNumberOfCards() {
        return cards.size();
    }

    private boolean isCardValid(Card card) {
        if (card == null || card.getName() == null) {
            return false;
        }

        return true;
    }

    private boolean canCardBeRegistrated(Card card) {
        if (isCardExisting(card)) {
            return false;
        }

        return (cards.size() + 1) <= MAX_NUMBER_OF_CARDS;
    }

    private boolean isCardExisting(Card card) {
        return cards.containsKey(card.getName());
    }

    private void addToTransactionsHistory(Transaction transaction) {
        if (transactions.size() == MAX_NUMBER_OF_TRANSACTIONS) {
            transactions.poll();
        }
        transactions.add(transaction);
    }
}
