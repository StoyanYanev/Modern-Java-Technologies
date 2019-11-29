package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.shopping.exceptions.ItemNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class ShoppingCartList implements ShoppingCart {

    private List<Item> items;

    public ShoppingCartList() {
        items = new ArrayList<>();
    }

    @Override
    public Collection<Item> getUniqueItems() {
        return new HashSet<>(items);

    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
        if (item != null) {
            if (!items.contains(item)) {
                throw new ItemNotFoundException();
            }
            items.remove(item);
        }
    }

    @Override
    public Collection<Item> getSortedItems() {
        Map<Item, Integer> occurrences = getItemsOccurrences();
        List<Item> uniqueItems = new ArrayList<>(occurrences.keySet());
        Collections.sort(uniqueItems, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare(occurrences.get(o2), occurrences.get(o1));
            }
        });

        return uniqueItems;
    }

    @Override
    public double getTotal() {
        double totalSum = 0;
        for (Item item : items) {
            totalSum += item.getPrice();
        }

        return totalSum;
    }

    private Map<Item, Integer> getItemsOccurrences() {
        Map<Item, Integer> occurrences = new HashMap<>();
        for (Item item : items) {
            if (!occurrences.containsKey(item))
                occurrences.put(item, 1);
            else
                occurrences.put(item, occurrences.get(item) + 1);
        }

        return occurrences;
    }
}