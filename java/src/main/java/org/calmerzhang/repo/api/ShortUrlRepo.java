package org.calmerzhang.repo.api;

import org.calmerzhang.repo.model.ShortUrlMappingPO;

/**
 * 短链保存接口
 *
 * @author calmerZhang
 * @create 2022/01/06 8:46 下午
 */
public interface ShortUrlRepo {

    /**
     * 保存长域名和短域名的映射
     * @param mappingPO
     * @return
     */
    int saveLongShortUrl(ShortUrlMappingPO mappingPO);

    /**
     * 根据长域名获取短域名
     */
    ShortUrlMappingPO getByLongUrl(String longUrl);

    /**
     * 根据短域名获取长域名
     * @param shortUrl
     * @return
     */
    ShortUrlMappingPO getByShortUrl(String shortUrl);

    String getDomain();
}
