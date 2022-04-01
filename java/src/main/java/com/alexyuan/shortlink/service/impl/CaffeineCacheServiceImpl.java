package com.alexyuan.shortlink.service.impl;

import com.alexyuan.shortlink.common.variant.CacheVariant;
import com.alexyuan.shortlink.config.CaffeineCacheConfig;
import com.alexyuan.shortlink.service.CaffeineCacheService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Service
public class CaffeineCacheServiceImpl implements CaffeineCacheService {

    private static final Logger logger = LoggerFactory.getLogger(CaffeineCacheServiceImpl.class);

    @Resource
    private CaffeineCacheConfig caffeineCacheConfig;

    @Autowired
    Cache<String, Object> caffeineCache;

    /**
     * @Description         将数据写入缓存, 以便下次访问使用
     * @param key           写入主键, 此处为唯一码(即 行机器码+列发号器码+唯一Code码)
     * @param result        写入数据, 此处为ResultVariant(即 结果集: 包括短URL 长URL 生成时间等数据)
     * @return
     */
    @Override
    public CacheVariant cachePut(String key, CacheVariant result) {
        logger.debug("Cache insert");
        if (null == result || StringUtils.isEmpty(key)) {
            logger.debug("Please Check Input Key And Cache Result");
            return null;
        }
        caffeineCache.put(key, result);
        return result;
    }

    /**
     * @Description         将数据从缓存读出, 缓存若为LRU模式该数据超时时间则会被重新计算
     * @param key           查询键, 此处为唯一码(即 行机器码+列机器码+唯一Code码)
     * @return
     */
    @Override
    public CacheVariant cacheGet(String key) {
        logger.debug("Cache find");
        if (null == key) {
            logger.debug("Cannot Get Null Value");
            return null;
        }
        CacheVariant cacheVariant = (CacheVariant) caffeineCache.asMap().get(key);
        // TODO:
        // 此处可扩展. 当前系统主要是依赖cache来保存数据的, 在本项目中, 存在需要将不常用的URL链接进行持久化的问题
        // 以避免该链接被从Cache中去除后永久丢失, 可以考虑将数据进行持久化, 使用搜索引擎作为二阶段查询方法, 将长期
        // 未被使用的url持久化并作为索引写入搜索引擎(可考虑使用批流一体的方式进行全量+增量的存储, 日内产生的新数据以
        // 流的方式写入). 在cache无法查询到的时候 通过查询搜索引擎来获取对应URL.

        // 基于URL拼接模式, 可以使用前缀树等方法构建查询加速
        return cacheVariant;
    }

    /**
     * @Description         本项目暂时无用, 效果为更新结果数据, 由于本项目采用唯一码(机器码+发号器码+自增Code码)方式
     *                      故不会存在重复主键.
     * @param result
     * @return
     */
    @Override
    public CacheVariant cacheUpdate(CacheVariant result) {
        return null;
    }

    /**
     * @Description         本项目暂时无用, 效果为删除结果数据
     * @return
     */
    @Override
    public CacheVariant cacheDelete(String key) {
        return null;
    }
}
