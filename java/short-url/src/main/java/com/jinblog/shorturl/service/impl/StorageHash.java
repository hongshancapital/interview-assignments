package com.jinblog.shorturl.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageHash implements com.jinblog.shorturl.service.Storage {

    private Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void save(String url, String shortUrl) {
        map.put(shortUrl, url);
    }

    @Override
    public String find(String url) {
        return map.get(url);
    }

    @Override
    public void delete(String shortUrl) {
        map.remove(shortUrl);
    }
}
