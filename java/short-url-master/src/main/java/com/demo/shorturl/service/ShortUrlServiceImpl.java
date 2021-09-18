package com.demo.shorturl.service;

import com.demo.shorturl.util.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Description: 生成短域名，获取长域名 实现
 * @author : wangjianzhi
 * Create on 2021/9/17
 */
@Service(value = "shortUrlServiceImpl")
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private static final String BASE_URL = "http://w.com/";

    ReentrantLock lock = new ReentrantLock();

    /**
     * 长域名为key短域名为value
     */
    static Map<String, String> shortUrlDataMap = new HashMap<>();
    /**
     * 短域名为key长域名为value
     */
    static Map<String, String> longUrlDataMap = new HashMap<>();
    /**
     * 功能描述: 通过长域名获取短域名
     * @param longUrl 长域名
     * @param maxRetryNum  短域名冲突重试次数
     * @return java.lang.String
     * @throws Exception Create on 2021/9/17
     * @author wangjianzhi
     */
    @Override
    public String getShortUrl(String longUrl, int maxRetryNum) throws Exception {
        lock.lock();
        try {
            String shortUrl = shortUrlDataMap.get(longUrl);
            if (StringUtils.isNotBlank(shortUrl)) {
                return shortUrl;
            }
            boolean retry = true;
            int retryNum = 0;
            while (retry) {
                if (retryNum > maxRetryNum) {
                    LOGGER.error("获取短域名异常");
                    throw new Exception("获取短域名异常");
                }
                shortUrl = BASE_URL + HashUtils.murmur32HashSeed(longUrl, (int) System.currentTimeMillis());
                if (StringUtils.isBlank(longUrlDataMap.get(shortUrl))) {
                    retry = false;
                }
                retryNum++;
            }
            shortUrlDataMap.put(longUrl, shortUrl);
            longUrlDataMap.put(shortUrl, longUrl);
            return shortUrl;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * 功能描述: 通过短域名获取长域名
     * @param shortUrl 短域名
     * @return java.lang.String
     * @throws Exception Create on 2021/9/17
     * @author wangjianzhi
     */
    @Override
    public String getLongUrl(String shortUrl) throws Exception {
        return longUrlDataMap.getOrDefault(shortUrl, "长域名不存在");
    }
}