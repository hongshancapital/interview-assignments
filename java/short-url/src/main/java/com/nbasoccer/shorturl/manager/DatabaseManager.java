package com.nbasoccer.shorturl.manager;

import com.nbasoccer.shorturl.utils.LRUCache;
import org.springframework.stereotype.Service;

@Service
public class DatabaseManager {

    private final static LRUCache cache = new LRUCache();

    public void getAndStoreUrlValue(String key, String originUrl){
        if(cache.containsKey(key)) return;
        cache.put(key, originUrl);
        //TODO: 并存入数据库
    }

    public String getUrlValue(String key){
        //如缓存未命中，将穿透读取数据库
        return cache.get(key);
    }

    public String getKeyByValue(String value){
        //如缓存未命中，将穿透读取数据库
        return cache.getKeyByValue(value);
    }

    public int getElementCount(){
        return cache.size();
    }
}
