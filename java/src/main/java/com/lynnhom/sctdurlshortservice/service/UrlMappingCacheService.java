package com.lynnhom.sctdurlshortservice.service;

import com.lynnhom.sctdurlshortservice.common.constant.Constants;
import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import com.lynnhom.sctdurlshortservice.common.utils.StringUtil;
import com.lynnhom.sctdurlshortservice.model.po.ShortKey;
import com.lynnhom.sctdurlshortservice.utils.UrlShortenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.LRUCache;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: URL映射缓存类
 * @author: Lynnhom
 * @create: 2021-11-02 17:48
 **/

@Slf4j
@Service
public class UrlMappingCacheService {

    /**
     * 最大数量限制
     * 生成的短链接数量限制在整数范围内，占用内存估算为2G
     */
    private static final int MAX_SIZE = Integer.MAX_VALUE;

    /**
     * 使用LRU-2缓存存储长短链接的映射关系
     * 短链接转长链接使用，读多写少，设置最大容量，根据访问频率进行自动淘汰
     * shortOriginalMap  shortKey -> originalUrl
     */
    private static final LRUCache<String, String> shortOriginalMap = new LRUCache(MAX_SIZE);

    /**
     * 使用ConcurrentHashMap存储长短链接有效期信息
     * 生成短链接时使用，读写量差别不大
     * originalShortMap  originalUrl -> shortKey
     */
    private static final ConcurrentHashMap<String, ShortKey> originalShortMap = new ConcurrentHashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock writeLock = readWriteLock.writeLock();

    /**
     * 根据短链接key获取对应的长链接地址
     * @param shortKey
     * @return
     */
    public String getOriginalUrl(String shortKey) {
        if (!shortOriginalMap.containsKey(shortKey)) {
            // 短链接不存在
            log.warn("getOriginalUrl, short url not exist, shortKey={}", shortKey);
            throw new BizException(RespCodeEnum.SHORT_URL_NOT_EXIST);
        }
        String originalUrl = shortOriginalMap.get(shortKey);
        ShortKey shortKeyInfo = originalShortMap.get(originalUrl);
        if (Objects.nonNull(shortKeyInfo)) {
            if (LocalDateTime.now().isAfter(shortKeyInfo.getExpireTime())) {
                // 链接有效期信息缺失或已过期，高并发查询接口，不做映射关系删除操作
                log.warn("getOriginalUrl, short url is expired, shortKey={}", shortKey);
                throw new BizException(RespCodeEnum.SHORT_URL_EXPIRED);
            }
        }
        return originalUrl;
    }

    /**
     * 根据原始链接地址，获取短链接地址
     * @param originalUrl
     * @return
     */
    public ShortKey getShortKey(String originalUrl) {
        ShortKey shortKey = null;
        ShortKey shortKeyInfo = originalShortMap.get(originalUrl);
        if (Objects.nonNull(shortKeyInfo)) {
            if (shortKeyInfo.getExpireTime().isAfter(LocalDateTime.now())) {
                // 有效的短链接信息
                shortKey = ShortKey.builder().build();
                BeanUtils.copyProperties(shortKeyInfo, shortKey);
            } else {
                /**
                 * 过期则同步删除长短链接的映射关系信息，先删除shortOriginalMap，再删除originalShortMap
                 * 即使出现宕机这样的系统异常，originalShortMap也会比shortOriginalMap的数据多
                 * 可通过异步的任务完成一致性同步逻辑
                 */
                shortOriginalMap.remove(shortKeyInfo.getShortKey());
                originalShortMap.remove(originalUrl);
            }
        }
        return shortKey;
    }

    /**
     * 更新短链接的有效时间
     * @param originalUrl
     * @param expireTime
     */
    public void updateExpireTime(String originalUrl, LocalDateTime expireTime) {
        ShortKey shortKey = originalShortMap.get(originalUrl);
        if (Objects.nonNull(shortKey)) {
            shortKey.setExpireTime(expireTime);
        }
    }


    /**
     * 新增一条长短链接的映射关系
     * @param appId
     * @param originalUrl
     * @param expireTime
     * @return
     */
    public String insertMapping(String appId, String originalUrl, LocalDateTime expireTime) {
        String shortKey = UrlShortenUtil.generate(originalUrl);
        // 出现hash冲突时，在原链接后面追加固定后缀
        String suffix = Constants.URL_SUFFIX;
        // 冲突处理次数计数
        int maxCount=0;

        /**
         * 增加写锁，分布式环境可以加分布式锁，锁的key为长链接originalUrl
         */
        writeLock.lock();
        try {
            while (shortOriginalMap.containsKey(shortKey)) {
                if (StringUtil.equals(shortOriginalMap.get(shortKey), originalUrl)) {
                    // shortKey对应的originalUrl已存在
                    return shortKey;
                }
                if (maxCount > Constants.MAX_SUFFIX_COUNT) {
                    log.error("insertMapping, hash crash time more than 20, originalUrl={}", originalUrl);
                    throw new BizException(RespCodeEnum.SERVER_ERROR);
                }
                shortKey = UrlShortenUtil.generate(originalUrl+suffix);
                suffix += Constants.URL_SUFFIX;
                ++maxCount;
            }
            ShortKey shortKeyInfo = ShortKey.builder()
                    .appId(appId)
                    .shortKey(shortKey)
                    .expireTime(expireTime)
                    .build();
            /**
             * 先增加originalShortMap，再增加shortOriginalMap，同样确保originalShortMap多于shortOriginalMap
             */
            originalShortMap.putIfAbsent(originalUrl, shortKeyInfo);
            shortOriginalMap.putIfAbsent(shortKey, originalUrl);
        } finally {
            writeLock.unlock();
        }

        return shortKey;
    }

    /**
     * 针对用户指定了失效时间的短链接，每小时清理一次过期的映射数据
     * 同步因LRU失效和系统异常导致的不一致 shortOriginalMap —> originalShortMap
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void clearExpireMapping() {
        Iterator<Map.Entry<String, ShortKey>> iterator = originalShortMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ShortKey> entry = iterator.next();
            String originalUrl = entry.getKey();
            ShortKey shortKeyInfo = entry.getValue();
            if (LocalDateTime.now().isAfter(shortKeyInfo.getExpireTime())) {
                // 映射数据失效, 删除映射关系数据
                shortOriginalMap.remove(shortKeyInfo.getShortKey());
                originalShortMap.remove(originalUrl);
            } else {
                // shortOriginalMap 中不存在的映射关系，同步从originalShortMap中删除
                if (!shortOriginalMap.containsKey(shortKeyInfo.getShortKey())) {
                    originalShortMap.remove(originalUrl);
                }
            }
        }
    }



}
