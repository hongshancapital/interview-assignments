package com.sequoiacap.shorturl.service;


import com.sequoiacap.shorturl.utils.BaseEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名服务
 */
@Service("urlService")
public class UrlService {
    //存储短域名映射
    protected final static Map<String, String> URL_MAP = new ConcurrentSkipListMap<>();

    //短域名KV反向，用于支持反查
    protected final static Map<String, String> KEY_MAP = new ConcurrentSkipListMap<>();

    //自动递增数
    protected static AtomicLong counter = new AtomicLong(0l);

    //8位短域名上限
    protected final static long MAX_COUNTER = 218340105584895L;

    //防止内存溢出，根据硬件配置设定
    @Value("${max_support_url_count}")
    protected long max_url_count;


    /**
     * 存储短域名
     * @param url
     * @return
     */
    public String getShortUrl(String url) {

        if(KEY_MAP.containsKey(url)){
            return KEY_MAP.get(url);
        }
        long index = counter.getAndIncrement();
        if(index>max_url_count||index>MAX_COUNTER){
            return null;
        }
        String key = BaseEncoder.encode(index);
        URL_MAP.put(key, url);
        KEY_MAP.put(url, key);
        return key;
    }

    /**
     * 读取短域名
     * @param shortKey
     * @return
     */
    public String getUrl(String shortKey){
        return URL_MAP.get(shortKey);
    }

}
