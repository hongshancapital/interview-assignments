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

    public AtomicLong conflictCounter =new AtomicLong(0);

    public AtomicLong conflictCounter1 =new AtomicLong(0);
    public AtomicLong conflictCounter2 =new AtomicLong(0);
    public AtomicLong conflictCounter3 =new AtomicLong(0);
    public AtomicLong conflictCounterReshort =new AtomicLong(0);

    public HashMap<String, Integer> reshortMap=new HashMap<String, Integer>();
    public Map<String, Object> context=new HashMap<String, Object>();
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
        conflictCounterReshort.incrementAndGet();
        int counter=0;
        try {
            for (int i = 0; i < 1000; i++) {
                counter = i;
                shortUrl = getInnerShortUrl(new StringBuffer().append(i).append(":").append(longUrl).toString());
                if (shortUrl != null) {
                    return shortUrl;
                }
            }
            throw new RuntimeException("暂无数据");
        }finally{
            reshortMap.put(longUrl, counter);
        }
    }



    public String getInnerShortUrl(String longUrl) {
        String[] shortUrls = Util.shortUrl(longUrl);
        //找到long url是否已经保存过
        //如果long url 被保存过则将 已经存在的短url 返回过去
        for(String s:shortUrls){
            if(hashMap.containsKey(s) && hashMap.get(s).equals(longUrl)){
                return s;
            }
        }

        //从返回的4段段链接中找到未被使用的短url
        String shortUrl = null;
        int i=0;
        for(String s:shortUrls){
            if(!hashMap.containsKey(s)){
                if(i>0){
                    //System.out.println("\n\nnow longUrl:["+longUrl+"] now shortUrl:"+s+"\t  old LongUrl:["+hashMap.get(shortUrls[i-1])+"] old shortUrl:"+shortUrls[i-1]+" i:"+i);
                    conflictCounter.incrementAndGet();
                    if(i==1){
                        conflictCounter1.incrementAndGet();
                    }else if(i==2){
                        conflictCounter2.incrementAndGet();
                    }else if(i==3){
                        conflictCounter3.incrementAndGet();
                    }
                }
                shortUrl = s;
                break;
            }
            i++;
        }
        return shortUrl;
    }

    public long getConflictCount(){
        return conflictCounter.get();
    }

    public Map<String, Object> getContext(){
        context.put("conflictCounter", conflictCounter.get());
        context.put("conflictCounter1", conflictCounter1.get());
        context.put("conflictCounter2", conflictCounter2.get());
        context.put("conflictCounter3", conflictCounter3.get());
        context.put("conflictCounterReshort", conflictCounterReshort.get());
        context.put("reshortMap", reshortMap);
        return context;
    }
}
