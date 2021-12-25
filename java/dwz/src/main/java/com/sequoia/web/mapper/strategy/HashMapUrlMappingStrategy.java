package com.sequoia.web.mapper.strategy;

import com.sequoia.web.mapper.ExpireNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class HashMapUrlMappingStrategy extends AbstractExpirableUrlMappingStrategy{
    Map<String, ExpireNode> hashMap = new ConcurrentHashMap<String, ExpireNode>();

    public HashMapUrlMappingStrategy(boolean enableExpire, long ttl, long initialDelay, long period, TimeUnit unit) {
        super(enableExpire, ttl, initialDelay, period, unit);
    }

    @Override
    protected Map<String, ExpireNode> getMap() {
        return hashMap;
    }
}
