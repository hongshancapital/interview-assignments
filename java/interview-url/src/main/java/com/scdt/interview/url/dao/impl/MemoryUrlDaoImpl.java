package com.scdt.interview.url.dao.impl;

import com.scdt.interview.url.dao.UrlDao;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: lijin
 * @date: 2021年10月09日
 */
@Repository
public class MemoryUrlDaoImpl implements UrlDao {

    private final ConcurrentHashMap<String, String> shortLongMap = new ConcurrentHashMap();

    private final ConcurrentHashMap<String, String> longShortMap = new ConcurrentHashMap();


    @Override
    public void saveUrl(String longUrl, String shortUrl) {

        shortLongMap.put(shortUrl, longUrl);
        longShortMap.put(longUrl, shortUrl);

    }

    @Override
    public boolean exists(String longUrl) {
        return longShortMap.containsKey(longUrl);
    }

    @Override
    public String getShortUrl(String longUrl) {
        return longShortMap.get(longUrl);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        return shortLongMap.get(shortUrl);
    }
}
