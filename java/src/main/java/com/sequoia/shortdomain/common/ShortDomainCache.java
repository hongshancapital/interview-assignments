package com.sequoia.shortdomain.common;

import java.util.concurrent.ConcurrentHashMap;

public class ShortDomainCache {

    private static ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>();

    public static void addCache(String key ,String value){
        chm.put(key, value);
    }

    public static String getCache(String key){
        return chm.get(key);
    }
}
