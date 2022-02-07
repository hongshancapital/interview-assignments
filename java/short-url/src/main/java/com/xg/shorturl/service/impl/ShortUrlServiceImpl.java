package com.xg.shorturl.service.impl;

import com.xg.shorturl.common.BaseErrorCode;
import com.xg.shorturl.common.BizException;
import com.xg.shorturl.constant.DomainConstants;
import com.xg.shorturl.dao.ShortUrlDao;
import com.xg.shorturl.service.ShortUrlService;
import com.xg.shorturl.utils.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xionggen
 */
@Component
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlDao shortUrlDao;

    @Value("${short.url.handle.duplicate.retry.max.times}")
    private int shortUrlHandleDuplicateRetryMaxTimes;

    @Override
    public String queryOrGenerateShortUrl(String originalUrl) {
        String shortUrl = shortUrlDao.queryShortUrl(originalUrl);
        if (null != shortUrl) {
            return shortUrl;
        }
        return handleDuplicate(originalUrl, 0);
    }

    @Override
    public String queryOriginalUrl(String shortUrl) {
        return shortUrlDao.queryOriginalUrl(shortUrl);
    }

    /**
     * 处理重复性问题：保存成功则直接返回，保存失败说明短链接重复，重新生成后进行重试，递归实现。
     * @param originalUrl 原始链接
     * @param retryTimes 重试次数
     * @return 新生成且保存成功的短链接，不会重复
     */
    private String handleDuplicate(String originalUrl, int retryTimes) {
        if (retryTimes >= shortUrlHandleDuplicateRetryMaxTimes) {
            throw new BizException("短链接重复，重试次数超过限制", BaseErrorCode.ERROR_SYSTEM_ERROR);
        }
        String shortSuffix = ShortUrlGenerator.generate(originalUrl + retryTimes);
        String shortUrl = addDefaultDomain(originalUrl, shortSuffix);
        int count = shortUrlDao.save(shortUrl, originalUrl);
        if (count == 1) {
            return shortUrl;
        }
        return handleDuplicate(originalUrl, retryTimes + 1);
    }

    private String addDefaultDomain(String originalUrl, String shortUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        if (originalUrl.startsWith(DomainConstants.HTTP_PREFIX)) {
            return stringBuilder.append(DomainConstants.HTTP_PREFIX)
                    .append(DomainConstants.DEFAULT_DOMAIN)
                    .append(DomainConstants.URL_CONNECTOR)
                    .append(shortUrl).toString();
        } else if (originalUrl.startsWith(DomainConstants.HTTPS_PREFIX)){
            return stringBuilder.append(DomainConstants.HTTPS_PREFIX)
                    .append(DomainConstants.DEFAULT_DOMAIN)
                    .append(DomainConstants.URL_CONNECTOR)
                    .append(shortUrl).toString();
        }
        return null;
    }

}
