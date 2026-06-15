package com.sequoia.web.mapper.strategy;

import com.sequoia.web.mapper.ExpireMap;
import com.sequoia.web.mapper.ExpireNode;
import com.sequoia.web.mapper.UrlMappingStrategy;

import java.util.concurrent.TimeUnit;

/**
 * 键值存储的抽象基类，各策略可继承此类
 */
public abstract class AbstractExpirableUrlMappingStrategy extends ExpireMap implements UrlMappingStrategy {

    public AbstractExpirableUrlMappingStrategy(boolean enableExpire, long ttl, long initialDelay, long period, TimeUnit unit) {
        super(enableExpire, ttl, initialDelay, period, unit);
    }

    @Override
    public String put(String key, String value) {
        ExpireNode previous = getMap().put(key, newNode(value));
        if(previous != null) return previous.getShorUrl();
        return null;
    }

    @Override
    public String get(String key) {
        ExpireNode expireNode = getMap().get(key);
        if(expireNode != null) {
            expireNode.setExpire(System.currentTimeMillis() + ttl);
            return expireNode.getShorUrl();
        }
        return null;
    }

    @Override
    public int size() {
        return getMap().size();
    }

}
