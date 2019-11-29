package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.util.Comparator;

public class DateOfferComparator implements Comparator<PriceStatistic> {

    @Override
    public int compare(PriceStatistic o1, PriceStatistic o2) {
        int result = o2.getDate().compareTo(o1.getDate());
        if (result == 0) {
            return o1.equals(o2) ? 0 : 1;
        }

        return result;
    }
}