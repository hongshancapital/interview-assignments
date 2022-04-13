package com.getao.urlconverter.service;

import com.getao.urlconverter.dto.vo.UrlCacheVO;

/**
 * 缓存需要两个KV对，一个存储长链接到短链接的映射，另一个存储短链接到长链接的映射
 */
public interface UrlCacheService {

    /**
     * @param oriUrl 短链接
     * @param resUrl 短链接
     * @return UrlCacheVO
     * @description 存入缓存
     */
    UrlCacheVO putInCache(String oriUrl, String resUrl);

    /**
     * @param oriUrl 短链接
     * @return GetShortUrlVO
     * @description 检查链接是否存在
     */
    Boolean exists(String oriUrl);

    /**
     * @param oriUrl 短链接
     * @return GetShortUrlVO
     * @description 获取长链接对应的短链接
     */
    UrlCacheVO getCache(String oriUrl);

    /**
     * @param oriUrl 短链接
     * @return GetShortUrlVO
     * @description 删除缓存内容
     */
    UrlCacheVO deleteCache(String oriUrl);
}