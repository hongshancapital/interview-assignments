package com.sequoia.web.mapper.strategy;

import com.sequoia.web.mapper.ExpireNode;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

public class SkipListMapUrlMappingStrategy extends AbstractExpirableUrlMappingStrategy{
    Map<String, ExpireNode> skipListMapMap = new ConcurrentSkipListMap<String, ExpireNode>();

    public SkipListMapUrlMappingStrategy(boolean enableExpire, long ttl, long initialDelay, long period, TimeUnit unit) {
        super(enableExpire, ttl, initialDelay, period, unit);
    }

    @Override
    protected Map<String, ExpireNode> getMap() {
        return skipListMapMap;
    }

}
