package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.util.Comparator;

public class PriceOfferComparator implements Comparator<Offer> {

    @Override
    public int compare(Offer o1, Offer o2) {
        int result = Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
        if (result == 0) {
            return o1.equals(o2) ? 0 : 1;
        }

        return result;
    }
}