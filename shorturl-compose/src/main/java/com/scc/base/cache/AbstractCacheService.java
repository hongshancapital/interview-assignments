package com.scc.base.cache;

import com.scc.base.service.Cache;

/**
 * @author renyunyi
 * @date 2022/4/24 5:36 PM
 * @description cache service implement template
 **/
public abstract class AbstractCacheService<K, V> implements Cache<K, V> {

    /**
     * cache capacity
     */
    protected int capacity;

    public AbstractCacheService(int capacity){
        this.capacity = capacity;
    }
}
