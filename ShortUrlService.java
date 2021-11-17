package com.example.demo.service;

import com.example.demo.util.UrlUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlService {

    private static volatile ConcurrentHashMap<String,String> longUrlShortUrlMap = new ConcurrentHashMap<>();
    private static volatile ConcurrentHashMap<String,String> shortUrlLongUrlMap = new ConcurrentHashMap<>();

    public String createShortUrl(String longUrl){
        String shortUrl = longUrlShortUrlMap.get(longUrl);

        // 先从内存中查找，找不到就生成shortUrl
        // 生成shortUrl，需要排重
        // 多线程同步，存shortUrl需要加锁
        if(StringUtils.isEmpty(shortUrl)){
            //生成shortUrl
            int i = 0 ;
            String[] shortUrlArr = UrlUtil.shortUrl(longUrl);
            // shortUrl排重
            while(StringUtils.isEmpty(shortUrl) || !StringUtils.isEmpty(shortUrlLongUrlMap.get(shortUrl))){

                if(i == shortUrlArr.length) {
                    i = 0;
                    shortUrlArr = UrlUtil.shortUrl(longUrl);
                }
                shortUrl= shortUrlArr[i];
                i++;
            }
            synchronized (longUrl){
                String value = longUrlShortUrlMap.putIfAbsent(longUrl,shortUrl);
                if(value==null) shortUrlLongUrlMap.putIfAbsent(shortUrl,longUrl);
            }
        }

        return shortUrl;
    }


    public String getLongUrl(String shortUrl){
        return longUrlShortUrlMap.get(shortUrl);
    }

}
