package com.example.shorturlservice.service;

import com.example.shorturlservice.domain.BStatusCode;
import com.example.shorturlservice.domain.BizException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description 短域名的存储和读取逻辑实现
 * @Author xingxing.yu
 * @Date 2022/04/15 17:49
 **/
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final int MAX_LENGTH = 128;
    private static final float RATE = 5.0f;
    private static BiMap<String, String> urlWarehouseMap = HashBiMap.create();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    @Override
    public String saveLongUrl(String longUrl) throws BizException {
        Lock write = readWriteLock.writeLock();
        String shortUrl = "";
        write.lock();
        try {
            if (!StringUtils.hasLength(longUrl)) {
                throw new BizException(BStatusCode.PARAM_NULL.getCode(), BStatusCode.PARAM_NULL.getDes());
            }
            if (longUrl.length() > MAX_LENGTH) {
                throw new BizException(BStatusCode.PARAM_LENGTH_LIMIT.getCode(), BStatusCode.PARAM_LENGTH_LIMIT.getDes());
            }
            //监控堆内存使用率
            Runtime r = Runtime.getRuntime();
            long total = r.totalMemory() / (1024 * 1024);
            long free = r.freeMemory() / (1024 * 1024);
            float freeRate = (float) free / total * 100;
            if (freeRate < RATE) {
                throw new BizException(BStatusCode.SERVER_ERROR.getCode(), BStatusCode.SERVER_ERROR.getDes());
            }

            //幂等
            if (urlWarehouseMap.containsKey(longUrl)) {
                return urlWarehouseMap.get(longUrl);
            }

            //返回一组4个短域名，循环添加，防止短域名碰撞，添加失败
            String[] urlRes = ShortUrlGenerator.shortUrl(longUrl);
            for (String url : urlRes) {
                try {
                    urlWarehouseMap.put(longUrl, url);
                    shortUrl = url;
                    break;
                } catch (IllegalArgumentException e) {
                    //日志略
                }
            }

            if (!StringUtils.hasLength(shortUrl)) {
                throw new BizException(BStatusCode.SERVER_ERROR.getCode(), BStatusCode.SERVER_ERROR.getDes());
            }

        } finally {
            write.unlock();
        }

        return shortUrl;
    }

    @Override
    public String getShortUrl(String shortUrl) {
        Lock read = readWriteLock.readLock();
        String longUrl;
        read.lock();
        try {
            longUrl = urlWarehouseMap.inverse().get(shortUrl);
        } finally {
            read.unlock();
        }
        return longUrl;
    }
}
