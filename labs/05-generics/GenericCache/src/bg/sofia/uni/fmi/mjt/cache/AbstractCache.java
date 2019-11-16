package bg.sofia.uni.fmi.mjt.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> implements Cache<K, V> {
    protected Map<K, V> cache;
    protected long capacity;

    private long successfulHits;
    private long allHits;

    public AbstractCache(long capacity) {
        cache = new HashMap<>();
        this.capacity = capacity;
        allHits = 0;
        successfulHits = 0;
    }

    @Override
    public V get(K key) {
        if (cache.containsKey(key)) {
            successfulHits++;
        }
        allHits++;

        return cache.get(key);
    }

    @Override
    public boolean remove(K key) {
        return cache.remove(key) != null;
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
        successfulHits = 0;
        allHits = 0;
    }

    @Override
    public double getHitRate() {
        if (allHits == 0) {
            return 0;
        }
        return (double) (successfulHits) / (allHits);
    }

    protected boolean isValidKey(K key) {
        return key != null;
    }

    protected boolean isValidValue(V value) {
        return value != null;
    }

    protected boolean isFull() {
        return cache.size() == capacity;
    }
}
