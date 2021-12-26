package com.hszb.shorturl.manager;

import com.hszb.shorturl.manager.generate.ShortCodeGenerator;
import com.hszb.shorturl.manager.generate.ShortUrlGeneratorFactory;
import com.hszb.shorturl.manager.storage.ShortUrlStorage;
import com.hszb.shorturl.model.ShortUrlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 5:55 下午
 * @Version: 1.0
 * @Description:
 */

@Component
@Slf4j
public class ShortUrlManager implements InitializingBean {

    private ShortCodeGenerator shortCodeGenerator;

    @Value("${application.domain}")
    private String domain;

    @Value("${generator.stratege}")
    private String generateStratege;

    /**
     * 长链接转短链接
     * @param longUrl
     * @return
     */
    public ShortUrlResult transferShortUrl (String longUrl) {
        // 先从缓存中取，缓存有直接返回，返回没有生成
        String shortCode = ShortUrlStorage.getInstance().queryShortUrl(longUrl);
        if (null == shortCode) {
            shortCode = shortCodeGenerator.generateCode(longUrl);
            ShortUrlStorage.getInstance().saveShortUrl(shortCode, longUrl);
        }
        if (null != shortCode) {
            return buildShortUrl(shortCode, longUrl);
        }
        return null;
    }

    /**
     * 根据短链接code查长链接
     * @param shortCode
     * @return
     */
    public String queryLongUrl (String shortCode) {
        return ShortUrlStorage.getInstance().queryLongUrl(shortCode);
    }

    /**
     * 构建完整短链接
     * @param shortCode
     * @return
     */
    private ShortUrlResult buildShortUrl (String shortCode, String longUrl) {
        StringBuilder shortUrl = new StringBuilder(domain);
        shortUrl.append(shortCode);
        ShortUrlResult shortUrlResult = new ShortUrlResult();
        shortUrlResult.setShortCode(shortCode);
        shortUrlResult.setShortUrl(shortUrl.toString());
        shortUrlResult.setLongUrl(longUrl);
        return shortUrlResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        shortCodeGenerator = ShortUrlGeneratorFactory.create(generateStratege);
    }
}
