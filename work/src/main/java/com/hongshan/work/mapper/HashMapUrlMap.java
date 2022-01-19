package com.hongshan.work.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 基于ConcurrentHashMap的实现
 */
public class HashMapUrlMap extends ExpireMap {
    Map<String, ExpireNode> hashMap = new ConcurrentHashMap<String, ExpireNode>();

    public HashMapUrlMap(boolean enableExpire, long ttl, long initialDelay, long period, TimeUnit unit) {
        super(enableExpire, ttl, initialDelay, period, unit);
    }

    @Override
    protected Map<String, ExpireNode> getMap() {
        return hashMap;
    }

    public String put(String key, String value) {
        ExpireNode previous = getMap().put(key, newNode(value));
        if(previous != null) return previous.getShorUrl();
        return null;
    }

    public String get(String key) {
        ExpireNode expireNode = getMap().get(key);
        if(expireNode != null) {
            expireNode.setExpire(System.currentTimeMillis() + ttl);
            return expireNode.getShorUrl();
        }
        return null;
    }


    public int size() {
        return getMap().size();
    }
}