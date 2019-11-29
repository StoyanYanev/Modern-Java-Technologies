package bg.sofia.uni.fmi.mjt.shopping.item;

import java.util.Objects;

public abstract class AbstractItem implements Item {
    private String name;
    private String description;
    private double price;

    public AbstractItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractItem)) {
            return false;
        }
        AbstractItem that = (AbstractItem) o;
        return Double.compare(that.price, price) == 0
                && name.equals(that.name)
                && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }
}