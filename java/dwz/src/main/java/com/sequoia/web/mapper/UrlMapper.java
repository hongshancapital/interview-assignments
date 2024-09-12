package com.sequoia.web.mapper;

import com.sequoia.web.mapper.strategy.AbstractExpirableUrlMappingStrategy;
import com.sequoia.web.mapper.strategy.HashMapUrlMappingStrategy;
import com.sequoia.web.mapper.strategy.SkipListMapUrlMappingStrategy;
import com.sequoia.web.mapper.strategy.TrieMapUrlMappingStrategy;

import java.util.concurrent.TimeUnit;

public class UrlMapper {
    private AbstractExpirableUrlMappingStrategy strategy;

    public enum Strategy {
        hashMap, trieMap, skipListMap
    }

    public UrlMapper(Strategy strategy, boolean enableExpire, long ttl, long initialDelay, long period, TimeUnit unit){
        switch (strategy) {
            case trieMap:
                this.strategy = new TrieMapUrlMappingStrategy(enableExpire, ttl, initialDelay, period, unit);
                break;
            case skipListMap:
                this.strategy = new SkipListMapUrlMappingStrategy(enableExpire, ttl, initialDelay, period, unit);
                break;
            default:
                this.strategy = new HashMapUrlMappingStrategy(enableExpire, ttl, initialDelay, period, unit);;
        }
    }

    public String put(String key, String value) {
        return strategy.put(key, value);
    }

    public String get(String key){
        return strategy.get(key);
    }

    public int size(){
        return strategy.size();
    }
}
