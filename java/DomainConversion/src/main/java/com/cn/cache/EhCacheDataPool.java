package com.cn.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wukui
 * @date 2021--12--30
 **/
@Component
public class EhCacheDataPool implements DataPool {
    @Autowired
    CacheManager cacheManager;

    public Cache getCache(){
        return  cacheManager.getCache("DomainCache");
    }

    @Override
    public String getData(String key) {
        return  getCache().get(key)== null? null :(String)getCache().get(key).getObjectValue();
    }

    @Override
    public void setData(String key, String value) {
        getCache().put(new Element(key,value));
    }

    @Override
    public void clearData() {
        getCache().removeAll();
    }

    @Override
    public boolean deleteData(String key) {
        return getCache().remove(key);
    }
}
