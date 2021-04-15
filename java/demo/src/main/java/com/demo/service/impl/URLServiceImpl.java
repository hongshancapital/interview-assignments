package com.demo.service.impl;

import com.demo.service.URLService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gouyunfei on 2021/4/14.
 */
@Service
public class URLServiceImpl implements URLService {

    ConcurrentHashMap<String, String> hashMap=new ConcurrentHashMap<String, String>();

//    private long freeSize = (long)(Runtime.getRuntime().freeMemory()*0.9);
    private long freeSize = (long)(Runtime.getRuntime().maxMemory()*0.7);
    private Object lock=new Object();

    AtomicLong hashmapSize=new AtomicLong(0);
    @Override
    public String getUrl(String shortUrl) {
        return hashMap.get(shortUrl);
    }

    @Override
    public String setUrl(String longUrl) {
        String shortUrl = getInnerShortUrl(longUrl);
        if(shortUrl==null){
            shortUrl = reShort(longUrl);
        }
        synchronized (lock) {
            if(hashMap.containsKey(shortUrl) && hashMap.get(shortUrl).equals(longUrl)) {
               return shortUrl;
            }else if(hashMap.containsKey(shortUrl)){
                shortUrl = reShort(longUrl);
            }
            long size=hashmapSize.addAndGet((longUrl.length()+8)*2);
            if(size>(freeSize)){
//                throw new RuntimeException("超过内存大小了" + size);
                return null;
            }

            hashMap.put(shortUrl, longUrl);
        }
        return shortUrl;
    }

    //重新缩小longUrl， 采用给longUrl加上一个前缀，生成新的短url
    public String reShort(String longUrl){
        String shortUrl = null;
        for(int i=0;i<1000;i++){

            shortUrl=getInnerShortUrl(new StringBuffer().append(i).append(":").append(longUrl).toString());
            if(shortUrl!=null){
                return shortUrl;
            }
        }
        throw new RuntimeException("暂无数据");
    }



    public String getInnerShortUrl(String longUrl) {
        String[] shortUrls = Util.shortUrl(longUrl);
        String shortUrl = null;
        //找到long url是否已经保存过
        for(String s:shortUrls){
            if(hashMap.containsKey(s) && hashMap.get(s).equals(longUrl)){
                return s;
            }
        }
        //找到短url
        for(String s:shortUrls){
            if(!hashMap.containsKey(s)){
                shortUrl = s;
                break;
            }
        }
        return shortUrl;
    }

}
