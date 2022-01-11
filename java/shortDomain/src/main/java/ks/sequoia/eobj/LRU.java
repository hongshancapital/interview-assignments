package ks.sequoia.eobj;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<K,V> {

    private final float loadFactory = 0.75f;
    private LinkedHashMap<K, V> map;

    public LRU(int maxCacheSize) {
        //HashMap达到容量就进行扩容，if ((size >= threshold)，因此需要+1
        int capacity = (int)Math.ceil(maxCacheSize / this.loadFactory) + 1;
        //public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder),accessOrder为true表示LRU
        map = new LinkedHashMap<K, V>(capacity, loadFactory, true) {
            //重写removeEldestEntry，当容量超过maxCacheSize会移除first
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxCacheSize;
            }
        };
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void remove(K key) {
        map.remove(key);
    }

    public boolean contain(K key) {
        return map.containsKey(key);
    }


    @Override
    public String toString() {
       return map.toString();
    }

    public int size(){
        return map.size();
    }

    public Map.Entry<K, V> getHead(){
        return map.entrySet().iterator().next();
    }

}
