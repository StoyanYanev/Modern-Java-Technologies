package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractOffer implements Offer {
    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;

    public AbstractOffer(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getShippingPrice() {
        return shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        return getPrice() + getShippingPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractOffer)) {
            return false;
        }
        AbstractOffer that = (AbstractOffer) o;
        return Double.compare(getTotalPrice(), that.getTotalPrice()) == 0
                && productName.equalsIgnoreCase(that.productName)
                && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, date, getTotalPrice());
    }
}