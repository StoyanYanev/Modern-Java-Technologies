package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.shopping.exceptions.ItemNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class ShoppingCartMap implements ShoppingCart {

    private Map<Item, Integer> items;

    public ShoppingCartMap() {
        items = new HashMap<>();
    }

    @Override
    public Collection<Item> getUniqueItems() {
        return items.keySet();
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            Integer occurrences = items.get(item);
            if (occurrences == null) {
                occurrences = 1;
            } else {
                occurrences++;
            }
            items.put(item, occurrences);
        }
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
        if (item != null) {
            if (!items.containsKey(item)) {
                throw new ItemNotFoundException();
            }
            Integer occurrences = items.get(item);
            if (occurrences == 1) {
                items.remove(item);
            } else {
                items.put(item, occurrences - 1);
            }
        }
    }

    @Override
    public double getTotal() {
        double totalSum = 0;
        for (Item item : items.keySet()) {
            totalSum += item.getPrice();
        }

        return totalSum;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Item> itemsList = new ArrayList<>(items.keySet());
        Collections.sort(itemsList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare(items.get(o2), items.get(o1));
            }
        });

        return itemsList;
    }
}