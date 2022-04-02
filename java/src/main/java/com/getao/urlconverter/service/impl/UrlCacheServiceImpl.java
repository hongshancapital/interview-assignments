package com.getao.urlconverter.service.impl;

import com.getao.urlconverter.config.CacheConfig;
import com.getao.urlconverter.dto.vo.UrlCacheVO;
import com.getao.urlconverter.exception.CacheException;
import com.getao.urlconverter.service.UrlCacheService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UrlCacheServiceImpl implements UrlCacheService {

    @Resource
    private CacheConfig cacheConfig;

    /**
     * 存储长链接到短链接的映射缓存
     */
    @Autowired
    Cache<String, Object> caffeineCache;

    /**
     * @description 将产出的结果存入缓存
     */
    @Override
    public UrlCacheVO putInCache(String oriUrl, String resUrl) {
        if (StringUtils.isEmpty(oriUrl)) {
            throw new CacheException("Cannot put empty key to cache");
        }
        // 因为这种情况下不会出现哈希冲突，将正向映射和反向映射都存进cache，方便通过短链接查长链接
        UrlCacheVO urlCacheVO = new UrlCacheVO(resUrl, oriUrl);
        caffeineCache.put(oriUrl, urlCacheVO);
        UrlCacheVO urlCacheVoReverse = new UrlCacheVO(oriUrl, resUrl);
        caffeineCache.put(resUrl, urlCacheVoReverse);
        return urlCacheVO;
    }

    /**
     * @description 检测key是否在缓存中
     */
    @Override
    public Boolean exists(String oriUrl) {
        if(StringUtils.isEmpty(oriUrl)) {
            return false;
        }
        return caffeineCache.asMap().get(oriUrl) != null;
    }

    /**
     * @description 根据key获取缓存中的内容
     */
    @Override
    public UrlCacheVO getCache(String oriUrl) {
        if (StringUtils.isEmpty(oriUrl) || !exists(oriUrl)) {
            return null;
        }
        return (UrlCacheVO) caffeineCache.asMap().get(oriUrl);
    }

    /**
     * @description 删除key在缓存中的内容
     */
    @Override
    public UrlCacheVO deleteCache(String oriUrl) {
        if (StringUtils.isEmpty(oriUrl) || !exists(oriUrl)) {
            return null;
        }
        UrlCacheVO urlCacheVO = (UrlCacheVO) caffeineCache.asMap().get(oriUrl);
        caffeineCache.asMap().remove(oriUrl);
        caffeineCache.asMap().remove(urlCacheVO.getResUrl());
        return urlCacheVO;
    }
}