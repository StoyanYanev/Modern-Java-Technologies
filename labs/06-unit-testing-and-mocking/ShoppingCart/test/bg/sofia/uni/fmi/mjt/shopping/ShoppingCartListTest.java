package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.exceptions.ItemNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;
import bg.sofia.uni.fmi.mjt.shopping.utils.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class ShoppingCartListTest {

    private ShoppingCart shoppingCart;

    @Before
    public void setUp() {
        shoppingCart = new ShoppingCartList();
    }

    @Test
    public void testGetUniqueItemsWithTwoIdenticalItemsShouldReturnOneUniqueItem() {
        shoppingCart.addItem(Utils.APPLE);
        shoppingCart.addItem(Utils.APPLE);

        Collection<Item> uniqueItem = shoppingCart.getUniqueItems();

        assertEquals(1, uniqueItem.size());
        assertTrue(uniqueItem.contains(Utils.APPLE));
    }

    @Test
    public void testSortedItemsShouldBeSortCorrectly() {
        shoppingCart.addItem(Utils.CHOCOLATE);
        shoppingCart.addItem(Utils.APPLE);
        shoppingCart.addItem(Utils.APPLE);

        Object[] sortedItems = shoppingCart.getSortedItems().toArray();

        assertEquals(2, sortedItems.length);
        assertEquals(Utils.APPLE, sortedItems[0]);
        assertEquals(Utils.CHOCOLATE, sortedItems[1]);
    }

    @Test
    public void testAddItemWithOneItemShouldBeAddToCart() {
        shoppingCart.addItem(Utils.APPLE);

        assertEquals(1, shoppingCart.getSortedItems().size());
    }

    @Test
    public void testAddItemWithNullItemShouldNotBeAddToCart() {
        shoppingCart.addItem(null);

        assertEquals(0, shoppingCart.getSortedItems().size());
    }

    @Test
    public void testRemoveItemShouldBeRemoveSuccessfully() throws ItemNotFoundException {
        shoppingCart.addItem(Utils.APPLE);

        shoppingCart.removeItem(Utils.APPLE);

        assertEquals(0, shoppingCart.getSortedItems().size());
    }

    @Test
    public void testRemoveItemWithTwoIdenticalItemsShouldLeftOne() throws ItemNotFoundException {
        shoppingCart.addItem(Utils.APPLE);
        shoppingCart.addItem(Utils.APPLE);

        shoppingCart.removeItem(Utils.APPLE);

        assertEquals(1, shoppingCart.getSortedItems().size());
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveItemWithNotExistingItemShouldThrowException() throws ItemNotFoundException {
        shoppingCart.removeItem(Utils.CHOCOLATE);
    }

    @Test
    public void testGetTotalShouldReturnCorrectTotalSum() {
        shoppingCart.addItem(Utils.APPLE);
        shoppingCart.addItem(Utils.CHOCOLATE);

        double expectedTotalSum = Utils.APPLE.getPrice() + Utils.CHOCOLATE.getPrice();

        assertEquals(expectedTotalSum, shoppingCart.getTotal(), Utils.DELTA);
    }
}