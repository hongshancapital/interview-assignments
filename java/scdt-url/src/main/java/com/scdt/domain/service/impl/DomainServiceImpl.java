package com.scdt.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.scdt.domain.service.IDomainService;
import com.scdt.domain.util.AssertUtil;
import com.scdt.domain.util.ConvertUrlUtils;
import com.scdt.domain.util.LogUtil;
import com.scdt.domain.util.UrlCacheStorageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Slf4j
@Service
public class DomainServiceImpl implements IDomainService {

    private static final int SEED = 62;
    private final String SHORT_DOMAIN = "https://t.cn/";

    @Value("${url.max-length}")
    private int urlMaxLength;

    AtomicLong counter = new AtomicLong(10);

    @Resource
    UrlCacheStorageHelper urlCacheStorageHelper;

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        AssertUtil.notNull(shortUrl,"参数shortUrl不能为null");
        LogUtil.debug(log,"getLongUrlByShortUrl,shortUrl:{}",shortUrl);

        String[] splitUrls = shortUrl.split("/");
        String shortKey = splitUrls[3];
        String longUrl = urlCacheStorageHelper.get(shortKey);
        AssertUtil.isTrue(StrUtil.isNotBlank(longUrl),"shortUrl未找到对应的长链接");

        return longUrl;
    }

    @Override
    public String getShortUrlByLongUrl(String longUrl) {
        AssertUtil.notNull(longUrl, "参数longUrl不能为null");
        LogUtil.debug(log, "getShortUrlByLongUrl，longUrl:{}", longUrl);

        String shortUrl = urlCacheStorageHelper.get(longUrl);
        if (StrUtil.isBlank(shortUrl)) {
            shortUrl = generateShortUrl(longUrl);
        }
        return convertFullUrl(shortUrl);
    }


    public String generateShortUrl(String longUrl) {
        Long num = counter.incrementAndGet();
        String shortUrl = ConvertUrlUtils.getShortUrl(num, SEED);
        //理论不会超过这个长度，测试 9000亿长度是8。假如超过了 兜底重置counter和缓存
        if (urlMaxLength < shortUrl.length()) {
            do {
                urlCacheStorageHelper.clear();
                num = counter.incrementAndGet();
                shortUrl = ConvertUrlUtils.getShortUrl(num, SEED);
            } while (counter.compareAndSet(num, 10L));
        }

        urlCacheStorageHelper.save(longUrl, shortUrl);
        return shortUrl;
    }

    public String convertFullUrl(String shortUrl) {
        return SHORT_DOMAIN + shortUrl;
    }
}
