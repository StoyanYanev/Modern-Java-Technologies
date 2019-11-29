package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class PremiumOffer extends AbstractOffer {
    private static final double MAX_DISCOUNT_PERCENT = 100.0;
    private static final double MIN_DISCOUNT_PERCENT = 0.0;
    private static final String FORMAT_PATTERN = "0.00";

    private double discount;

    public PremiumOffer(String productName, LocalDate date, String description,
                        double price, double shippingPrice, double discount) {
        super(productName, date, description, price, shippingPrice);
        if (discount >= MIN_DISCOUNT_PERCENT && discount <= MAX_DISCOUNT_PERCENT) {
            DecimalFormat decimalFormat = new DecimalFormat(FORMAT_PATTERN);
            this.discount = Double.parseDouble(decimalFormat.format(discount));
        }
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() - ((discount / MAX_DISCOUNT_PERCENT) * super.getTotalPrice());
    }
}