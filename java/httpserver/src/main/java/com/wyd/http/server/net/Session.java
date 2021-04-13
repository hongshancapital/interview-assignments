package com.wyd.http.server.net;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class Session {

    private final Map<String, String> map = new WeakHashMap<>();
//    Cache<String,String> cache = CacheBuilder.newBuilder().build();
//    CacheBuilder

   private final Cache<String, String> build = CacheBuilder
            .newBuilder()
            .maximumSize(100)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();


    public Cache getCache() {
        return build;
    }


}
