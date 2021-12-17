package com.interview.repository;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description :
 * @Author: nyacc
 * @Date: 2021/12/17 13:04
 */

@Repository
public class DomainRepository {

    private ConcurrentHashMap<String,String> shortToLongUrlMap=new ConcurrentHashMap<String, String>(500);

    private ConcurrentHashMap<String,String> longToShortUrlMap=new ConcurrentHashMap<String,String>(500);

    public boolean addUrl(String shortUrl,String longUrl){
        boolean isSuccess = shortToLongUrlMap.putIfAbsent(shortUrl, longUrl)==null;
        if(isSuccess){
            longToShortUrlMap.put(longUrl,shortUrl);
        }
        return isSuccess;
    }

    public String getShortUrlByLong(String longUrl){
        return longToShortUrlMap.get(longUrl);
    }

    public String getLongUrlByShort(String shortUrl){
        return shortToLongUrlMap.get(shortUrl);
    }

}
