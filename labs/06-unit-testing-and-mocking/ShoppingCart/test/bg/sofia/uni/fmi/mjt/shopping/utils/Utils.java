package bg.sofia.uni.fmi.mjt.shopping.utils;

import bg.sofia.uni.fmi.mjt.shopping.item.AbstractItem;
import bg.sofia.uni.fmi.mjt.shopping.item.Apple;
import bg.sofia.uni.fmi.mjt.shopping.item.Chocolate;

public final class Utils {
    public static final double DELTA = 0.01;

    public static final AbstractItem APPLE = new Apple("Red", "Red apple", 1.2);
    public static final AbstractItem APPLE_WITH_DIFFERENT_NAME = new Apple("Green", "Red apple", 1.2);
    public static final AbstractItem CHOCOLATE = new Chocolate("Lindor", "Black chocolate", 5.2);
    public static final AbstractItem CHOCOLATE_WITH_DIFFERENT_NAME = new Chocolate("Milka", "Black chocolate", 5.2);

    private Utils() {

    }
}