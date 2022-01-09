package com.sequoia.shorturl.web.repository;

import cn.hutool.core.util.StrUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yanggj
 * @Description: 保存url映射关系
 * @Date: 2022/1/5 21:29
 * @Version: 1.0.0
 */
public class UrlConvertorMapping {

    // key: shortUrl value: ExpireNode(longUrl,ttl)
    private static final Map<String, ExpireNode<String>> URL_MAP = new ConcurrentHashMap<>();
    // 过期时间
    private final long ttl;
    // 定时处理过期短url
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public UrlConvertorMapping(long ttl, long period, TimeUnit timeUnit) {
        this.ttl = ttl;
        scheduledExecutorService.scheduleAtFixedRate(new UrlMappingCleanTask(), 0,period, timeUnit);
    }

    public String get(String shortUrl) {
        ExpireNode<String> node = URL_MAP.get(shortUrl);
        if (node == null) return StrUtil.EMPTY;
        // 续约,延长过期时间,并发不高时,不对 System.currentTimeMillis() 过度优化
        node.setExpireTime(System.currentTimeMillis() + ttl);
        return node.getKey();
    }

    public String put(String key, String value) {
        ExpireNode<String> expireNode = URL_MAP.put(key, new ExpireNode<>(value, System.currentTimeMillis()));
        // expireNode为空则返回空串
        return expireNode == null ? StrUtil.EMPTY : expireNode.getKey();
    }

    public int size() {
        return URL_MAP.size();
    }

    /**
     * 缓存清理任务
     */
    static class UrlMappingCleanTask implements Runnable {
        @Override
        public void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (URL_MAP.size() != 0) {
                URL_MAP.keySet().removeIf(key -> URL_MAP.get(key).getExpireTime() <= currentTimeMillis);
            }
        }
    }

}
