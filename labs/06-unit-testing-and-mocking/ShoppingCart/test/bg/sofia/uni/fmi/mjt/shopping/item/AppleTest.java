package bg.sofia.uni.fmi.mjt.shopping.item;

import bg.sofia.uni.fmi.mjt.shopping.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppleTest {
    @Test
    public void testEqualsWithEqualsObjects() {
        assertTrue(Utils.APPLE.equals(Utils.APPLE));
    }

    @Test
    public void testEqualsWithObjectsWithDifferentNames() {
        assertFalse(Utils.APPLE.equals(Utils.APPLE_WITH_DIFFERENT_NAME));
    }
}