package com.mhy.db;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalDatabase {

    /**
     * 长短链接存储
     * key：短链接shortUrl  value：长链接longUrl
     */
    public static Map<String, String> DOMAIN_WAREHOUSE = new ConcurrentHashMap<>();
}
