package bg.sofia.uni.fmi.mjt.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomReplacementCache<K, V> extends AbstractCache<K, V> {

    public RandomReplacementCache(long capacity) {
        super(capacity);
    }

    @Override
    public void set(K key, V value) {
        if (!isValidKey(key) || !isValidValue(value)) {
            return;
        }
        if (isFull()) {
            evictElement();
        }
        cache.put(key, value);
    }

    @Override
    public int getUsesCount(K key) {
        throw new UnsupportedOperationException();
    }

    private void evictElement() {
        List<K> keysAsArray = new ArrayList<>(cache.keySet());
        Random r = new Random();
        cache.remove(keysAsArray.get(r.nextInt(keysAsArray.size())));
    }
}
