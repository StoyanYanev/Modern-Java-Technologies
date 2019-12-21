package bg.sofia.uni.fmi.mjt.christmas;

import java.util.concurrent.atomic.AtomicInteger;

public class Elf extends Thread {
    private static final int TIMEOUT = 100;

    private int id;
    private final Workshop workshop;
    private AtomicInteger numberOfCraftedGifts;

    public Elf(int id, Workshop workshop) {
        this.id = id;
        this.workshop = workshop;
        numberOfCraftedGifts = new AtomicInteger(0);
    }

    /**
     * Gets a wish from the backlog and creates the wanted gift.
     **/
    public void craftGift() throws InterruptedException {
        synchronized (workshop) {
            Gift currentGift = workshop.nextGift();
            while (currentGift == null && !workshop.isChristmasTime()) {
                workshop.wait(TIMEOUT);
                currentGift = workshop.nextGift();
            }
            if (currentGift != null) {
                Thread.sleep(currentGift.getCraftTime());
                numberOfCraftedGifts.getAndIncrement();
            }
        }
    }

    /**
     * Returns the total number of gifts that the given elf has crafted.
     **/
    public int getTotalGiftsCrafted() {
        return numberOfCraftedGifts.get();
    }

    @Override
    public void run() {
        while (!workshop.isChristmasTime() || workshop.getGiftsCountInBacklog() != 0) {
            try {
                craftGift();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}