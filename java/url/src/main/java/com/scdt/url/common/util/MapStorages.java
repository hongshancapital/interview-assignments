package com.scdt.url.common.util;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import com.googlecode.concurrentlinkedhashmap.Weighers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 双key的LRU 哈希表存储器
 * @author easten
 * @date 2021/12/09
 */
public class MapStorages<K1, V, K2, S extends Function<V, K2>> {

    protected S getSupplementalKeyFunction() {
        return null;
    }

    public MapStorages(int maximumWeightedCapacity){
       mainCollections = new ConcurrentLinkedHashMap.Builder<K1, V>()
                .maximumWeightedCapacity(maximumWeightedCapacity)
                .listener(listener)
                .weigher(Weighers.singleton())
                .build();
    }

    /**
     * 淘汰主容器的key后也淘汰补充容器的key
     */
    EvictionListener<K1, V> listener = new EvictionListener<K1, V>() {
        @Override
        public void onEviction(K1 k1, V value) {
            if (getSupplementalKeyFunction() != null) {
                var supplementalKey = getSupplementalKeyFunction().apply(value);
                supplementalCollections.remove(supplementalKey);
            }
        }
    };
    private final Map<K1, V> mainCollections;
    private final Map<K2, K1> supplementalCollections = new ConcurrentHashMap<>();

    public V putIfAbsent(K1 key, V value) {
        return mainCollections.computeIfAbsent(key, newKey -> {
            if (getSupplementalKeyFunction() != null) {
                var supplementalKey = getSupplementalKeyFunction().apply(value);
                supplementalCollections.putIfAbsent(supplementalKey, newKey);
            }
            return value;
        });
    }

    public V get(K1 key) {
        return mainCollections.get(key);
    }


    /**
     * 根据补充容器查询 主容器的key再查询value
     */
    public V getSupplemental(K2 key) {
        var mainKey = supplementalCollections.get(key);
        if (mainKey == null) {
            return null;
        }
        return mainCollections.get(mainKey);
    }
}