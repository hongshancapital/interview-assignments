package com.sequoia.web.mapper.strategy;

import com.romix.scala.collection.concurrent.TrieMap;
import com.sequoia.web.mapper.ExpireNode;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TrieMapUrlMappingStrategy extends AbstractExpirableUrlMappingStrategy{
    Map trie = new TrieMap <String, ExpireNode> ();

    public TrieMapUrlMappingStrategy(boolean enableExpire, long ttl, long initialDelay, long period, TimeUnit unit) {
        super(enableExpire, ttl, initialDelay, period, unit);
    }

    @Override
    protected Map<String, ExpireNode> getMap() {
        return trie;
    }
}
