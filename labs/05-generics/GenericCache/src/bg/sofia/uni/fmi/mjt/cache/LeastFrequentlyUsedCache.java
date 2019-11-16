package bg.sofia.uni.fmi.mjt.cache;

import java.util.HashMap;
import java.util.Map;

public class LeastFrequentlyUsedCache<K, V> extends AbstractCache<K, V> {
    private Map<K, Integer> cacheCount;

    public LeastFrequentlyUsedCache(long capacity) {
        super(capacity);
        cacheCount = new HashMap<>();
    }

    @Override
    public V get(K key) {
        increaseCount(key);
        return super.get(key);
    }

    @Override
    public void set(K key, V value) {
        if (!isValidKey(key) || !isValidValue(value)) {
            return;
        }

        if (isFull()) {
            cache.remove(getLeastFrequentlyUsedKey());
        }

        increaseCount(key);
        cache.put(key, value);
    }

    @Override
    public boolean remove(K key) {
        return super.remove(key) &&
                cacheCount.remove(key) != null;
    }

    @Override
    public void clear() {
        super.clear();
        cacheCount.clear();
    }

    @Override
    public int getUsesCount(K key) {
        if (!cacheCount.containsKey(key)) {
            return 0;
        }

        return cacheCount.get(key);
    }

    private K getLeastFrequentlyUsedKey() {
        K currentKey = null;
        int minFrequency = Integer.MAX_VALUE;

        for (Map.Entry<K, Integer> entry : cacheCount.entrySet()) {
            if (entry.getValue() <= minFrequency) {
                minFrequency = entry.getValue();
                currentKey = entry.getKey();
            }
        }

        return currentKey;
    }

    private void increaseCount(K key) {
        if (!isValidKey(key)) {
            return;
        }
        if (cacheCount.containsKey(key)) {
            cacheCount.put(key, cacheCount.get(key) + 1);
        } else {
            cacheCount.put(key, 1);
        }
    }
}
