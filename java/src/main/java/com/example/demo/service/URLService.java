package com.example.demo.service;

import com.example.demo.model.URL;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class URLService {

    private static AtomicLong id = new AtomicLong();
    private static LRUCache cache = new LRUCache(1000000L);
    public BaseResponse queryShortURL(String originalURL){
        String shortURL = "";
        synchronized (this){
            if(cache.getIdByOriginalURL(originalURL) != Long.MIN_VALUE){
                long id = cache.getIdByOriginalURL(originalURL);
                shortURL = cache.get(id).getShortURL();
                return BaseResponse.success(shortURL);
            }
        }
        shortURL = this.idToShortURL(id.incrementAndGet());
        if(shortURL.length() > 8){
            return BaseResponse.error(ResultCode.SHORTURL_LIMIT, ResultCode.SHORTURL_LIMIT.getMessage());
        }
        URL url = new URL();
        url.setId(id.get());
        url.setOriginalURL(originalURL);
        url.setShortURL(shortURL);
        cache.put(url.getId(), url);
        return BaseResponse.success(shortURL);
    }

    public BaseResponse queryOriginalURL(String shortURL){
        if(!isExistInCache(shortURL)){
            return BaseResponse.error(ResultCode.SHORTURL_NOT_FOUND, ResultCode.SHORTURL_NOT_FOUND.getMessage());
        }
        long key = this.shortURLtoID(shortURL);
        String originalURL = cache.get(key).getOriginalURL();
        return BaseResponse.success(originalURL);
    }

    public boolean isExistInCache(String shortURL){
        long key = this.shortURLtoID(shortURL);
        return cache.get(key) != null;
    }

    long shortURLtoID(String shortURL){
        long id = 0;
        char[] shortURLArr = shortURL.toCharArray();
        for (int i=0; i < shortURL.length(); i++){
            if(id >= Long.MAX_VALUE || id < 0){
                return id = Long.MAX_VALUE;
            }
            if ('a' <= shortURLArr[i] && shortURLArr[i] <= 'z')
                id = id*62 + shortURLArr[i] - 'a' ;
            if ('A' <= shortURLArr[i] && shortURLArr[i] <= 'Z')
                id = id*62 + shortURLArr[i] - 'A' + 26;
            if ('0' <= shortURLArr[i] && shortURLArr[i] <= '9')
                id = id*62 + shortURLArr[i] - '0' + 52;
        }
        return id;
    }

    String idToShortURL(long n){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] map = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        while (n > 0){
            sb.append(map[(int) (n%62)]);
            n = n/62;
        }
        String shortURL = sb.toString();
        shortURL = reverse(shortURL);
        return shortURL;
    }

    String reverse(String str){
        if(str == null || str.length() == 0) {
            return str;
        }
        char[] arr = str.toCharArray();
        int l = 0;
        int r = arr.length - 1;
        while (l <= r){
            swap(arr, l++, r--);
        }
        return String.valueOf(arr);
    }

    synchronized void swap(char[] arr, int i, int j){
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
