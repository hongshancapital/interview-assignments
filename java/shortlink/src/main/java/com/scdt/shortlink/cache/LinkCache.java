package com.scdt.shortlink.cache;

public interface LinkCache {
    void save(String key, String url);

    String getUrl(String key);
}
