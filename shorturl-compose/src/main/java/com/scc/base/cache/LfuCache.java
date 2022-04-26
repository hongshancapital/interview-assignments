package com.scc.base.cache;

import java.util.*;

/**
 * @author renyunyi
 * @date 2022/4/24 5:13 PM
 * @description least frequently used for cached url
 **/
public class LfuCache<K, V> extends AbstractCacheService<K, V>{

    private final Map<K, CacheNode<K, V>> map;

    private final TreeSet<CacheNode<K, V>> treeSet;

    public LfuCache(int capacity) {
        super(capacity);
        map = new HashMap<>(capacity);
        treeSet = new TreeSet<>();
    }

    /**
     * thread safety to put
     * @param key cache key
     * @param value cache value
     */
    @Override
    public synchronized void put(K key, V value) {
        CacheNode<K, V> cacheNode = map.get(key);
        //k, v not exist
        if (cacheNode == null){
            //compare size and capacity
            if (map.size() >= capacity){
                //delete least frequency used, if count is equal, delete least recently used
                map.remove(treeSet.first().key);
                treeSet.pollFirst();
            }
            cacheNode = new CacheNode<>(key, value);

        }else {
            //k,v exist
            treeSet.remove(cacheNode);
            cacheNode.setValue(value);
        }

        //update
        treeSet.add(cacheNode);
        map.put(key, cacheNode);
    }


    /**
     * thread safety to get
     * @param key cache key
     * @return value
     */
    @Override
    public synchronized V get(K key) {
        CacheNode<K, V> cacheNode = map.get(key);
        if (cacheNode == null){
            return null;
        }

        treeSet.remove(cacheNode);
        V value = cacheNode.getValue();

        //update tree
        treeSet.add(cacheNode);
        return value;
    }

    /**
     * inner class to stop
     * @param <K> user to define type
     * @param <V> user to define type
     */
    public static class CacheNode<K, V> implements Comparable<CacheNode<K, V>>{

        /**
         * data key
         */
        private final K key;

        /**
         * data value
         */
        private V value;

        /**
         * least access time
         */
        private long lastAccessTime;

        /**
         *  visit data count
         */
        private int count;

        public CacheNode(K key, V value){
            this.key = key;
            this.value = value;
        }

        public void setValue(V value){
            this.value = value;
            this.lastAccessTime =System.currentTimeMillis();
            this.count ++;
        }

        public V getValue() {
            lastAccessTime = System.currentTimeMillis();
            count ++;
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CacheNode<?, ?> cacheNode = (CacheNode<?, ?>) o;
            //this based key and value
            return  Objects.equals(key, cacheNode.key)
                    && Objects.equals(value, cacheNode.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value, lastAccessTime, count);
        }

        @Override
        public int compareTo(CacheNode<K, V> o) {
            int res = Integer.compare(this.count, o.count);
            return res != 0 ? res : Long.compare(this.lastAccessTime, o.lastAccessTime);
        }
    }

}
