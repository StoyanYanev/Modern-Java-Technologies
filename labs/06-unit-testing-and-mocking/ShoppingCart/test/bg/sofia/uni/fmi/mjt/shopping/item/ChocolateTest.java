package bg.sofia.uni.fmi.mjt.shopping.item;

import bg.sofia.uni.fmi.mjt.shopping.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChocolateTest {

    @Test
    public void testEqualsWithEqualsObjects() {
        assertTrue(Utils.CHOCOLATE.equals(Utils.CHOCOLATE));
    }

    @Test
    public void testEqualsWithObjectsWithDifferentNames() {
        assertFalse(Utils.CHOCOLATE.equals(Utils.CHOCOLATE_WITH_DIFFERENT_NAME));
    }
}