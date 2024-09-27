package com.url.serveice.impl;

import com.url.serveice.UrlExchangeService;
import com.url.util.DataConversionUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UrlExchangeServiceImpl implements UrlExchangeService {

    private final static Long min=3521614606208L;
    private final static Long max=218340105584895L;

    /**
     * 上一次的发号器
     */
    private AtomicLong lastNum=new AtomicLong(min);

    /**
     * 长连接与号的对应关系
     */
    private Map<String,Long> longUrlMap = new ConcurrentHashMap<>();

    @Override
    public String getShort(String longUrl) {
        String shortUrl;
        if(longUrlMap.containsKey(longUrl)){
            Long aLong = longUrlMap.get(longUrl);
            shortUrl = DataConversionUtil.converse(aLong);
        }else{
            long l = lastNum.incrementAndGet();
            if(l<=max){
                longUrlMap.put(longUrl,l);
                shortUrl = DataConversionUtil.converse(l);
            }else{
                //如果号码发完，就重头开始。暂定方案，有优化空间。
                lastNum.set(min);
                shortUrl = DataConversionUtil.converse(min);
            }
        }
        return shortUrl;
    }

    @Override
    public String getLong(String shortUrl) {
        long l = DataConversionUtil.reConverse(shortUrl);
        Set<Map.Entry<String, Long>> entries = longUrlMap.entrySet();
        List<Map.Entry<String, Long>> collect = entries.stream().filter((entry) -> {
            if (entry.getValue().equals(l)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        return collect.get(0).getKey();
    }
}
