package com.zc.shorturl.service.impl;

import com.zc.shorturl.config.ShortUrlConfig;
import com.zc.shorturl.constant.ShortUrlConstant;
import com.zc.shorturl.service.ShortUrlService;
import com.zc.shorturl.snowflake.IdGenerator;
import com.zc.shorturl.snowflake.common.IdStatus;
import com.zc.shorturl.snowflake.common.Result;
import com.zc.shorturl.utils.BaseConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final IdGenerator idGenerator;
    private final ShortUrlConfig shortUrlConfig;

    /**
     * 根据longUrl创建shortUrl。 如果有缓存，则直接返回；否则创建新的shortUrl并缓存
     * @param longUrl
     * @return
     */
    @Override
    @Cacheable(value = ShortUrlConstant.LONG_TO_SHORT_CACHE_NAME, key = "#longUrl")
    public String createShortUrl(String longUrl) {
        Result result = idGenerator.nextId();
        if (!result.getIdStatus().equals(IdStatus.SUCCESS)) {
            log.error(result.getMessage());
            return "";
        }
        String base62 = BaseConvertUtils.decimal2Base62(result.getId());
        String shortUrl = shortUrlConfig.getDefaultCustomDomain() + "/" + base62;
        // TODO:生产屏蔽日志
        log.info("createShortUrl, longUrl:{}, shortUrl:{}, id:{}", longUrl, shortUrl, result.getId());
        return base62;
    }

//    @Override
//    public String getLongUrl(String shortUrl){
//        String longUrl = getLongUrlFromCache(shortUrl);
//        return longUrl;
//    }

    /**
     * 根据shortUrl从缓存中获取原始longUrl，没找到则返回空
     * @param shortUrl
     * @return
     */
    @Override
    @Cacheable(value = ShortUrlConstant.SHORT_TO_LONG_CACHE_NAME, key = "#shortUrl")
    public String getLongUrlFromCache(String shortUrl) {
        // 如果缓存没找到，则返回空
        return "";
    }

    /**
     * 更新short2long缓存
     * @param shortUrl
     * @param longUrl
     * @return
     */
    @Override
    @CachePut(value = ShortUrlConstant.SHORT_TO_LONG_CACHE_NAME, key = "#shortUrl")
    public String updateShort2Long(String shortUrl, String longUrl) {
        return longUrl;
    }
}
