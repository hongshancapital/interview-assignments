package com.hongshan.url.impl;

import com.hongshan.url.service.ShortUrlService;
import com.hongshan.util.UrlHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * @author lilejun
 * 短连接实现类
 */

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final Logger logger = Logger.getLogger(ShortUrlServiceImpl.class);


    @Override
    public String getShortUrl(String commonUrl, Integer length) {
        try {
            return UrlHelper.handleShortUrl(commonUrl, length);
        } catch (Exception e) {
            logger.error("生成短域名失败", e);
        }
        return null;
    }

    @Override
    public String getCommonUrl(String shortUrl) {
        try {
            return UrlHelper.cache.getIfPresent(shortUrl);
        } catch (Exception e) {
            logger.error("根据短域名获取长域名出现异常", e);
        }
        return null;
    }
}
