package com.my.linkapi.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MapSaveService {
    void save(String key, String value);

    String getData(String key);
}
