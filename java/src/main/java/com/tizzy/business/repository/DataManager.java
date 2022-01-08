package com.tizzy.business.repository;

import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.Weighers;

public class DataManager {

    private final static long dataLimit = 1000000L;
    private final static long cacheLimit = 10000L;
    private static DataManager instance = null;

    //短码code到url长链的数据表
    private ConcurrentLinkedHashMap<Long, Url> memDB;
//    = new ConcurrentLinkedHashMap.Builder<Long, Url>()
//            .maximumWeightedCapacity(dataLimit).weigher(Weighers.singleton())
//            .build();

    //长链url到短码code的缓存
    private ConcurrentLinkedHashMap<String, Long> urlCache;
//    = new ConcurrentLinkedHashMap.Builder<String, Long>()
//            .maximumWeightedCapacity(cacheLimit).weigher(Weighers.singleton())
//            .build();

    private DataManager() {
        memDB = new ConcurrentLinkedHashMap.Builder<Long, Url>()
                .maximumWeightedCapacity(dataLimit).weigher(Weighers.singleton())
                .build();

        urlCache = new ConcurrentLinkedHashMap.Builder<String, Long>()
                .maximumWeightedCapacity(cacheLimit).weigher(Weighers.singleton())
                .build();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (DataManager.class) {
                if (instance == null) {
                    instance = new DataManager();
                    return instance;
                }
            }
        }
        return instance;
    }

    public long getFromCache(String longUrl) {
        long result = 0L;
        Long urlCode = urlCache.get(longUrl);

        if (urlCode != null) {
            result = urlCode;
        }

        if (null != urlCode && urlCode > 0) {
            Url urlData = memDB.get(urlCode);

            //数据过期，手动移除
            if (System.currentTimeMillis() >= urlData.getExpiresDate()) {
                memDB.remove(urlCode);
                urlCache.remove(longUrl);
                result = 0;
            }
        }
        return result;
    }

    public void put(long urlCode, String longUrl, long expiresTime) {
        memDB.put(urlCode, new Url(longUrl, expiresTime));
        urlCache.put(longUrl, urlCode);
    }

    public String get(long urlCode) {
        Url urlData = memDB.get(urlCode);
        String longUrl = null;

        if (urlData != null) {
            longUrl = urlData.getLongUrl();

            //数据过期，手动移除
            if (System.currentTimeMillis() >= urlData.getExpiresDate()) {
                memDB.remove(urlCode);
                urlCache.remove(longUrl);
                longUrl = "";
            }
        }
        return longUrl;
    }

}
