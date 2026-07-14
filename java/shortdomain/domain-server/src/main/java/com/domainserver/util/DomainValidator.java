package com.domainserver.util;

import com.domaincore.constants.BusinessConstants;
import com.domaincore.constants.ErrorInfoEnum;
import com.domaincore.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * 短域名服务校验
 *
 * @author Administrator
 * @Date 2021/9/21
 */
public class DomainValidator {
    /**
     * 校验常域名
     *
     * @param longUrl 长域名
     */
    public static void validateLongUrl(String longUrl) {
        if (StringUtils.isEmpty(longUrl)) {
            throw new BusinessException(ErrorInfoEnum.URL_EMPTY_ERROR);
        }
    }

    /**
     * 校验短域名
     *
     * @param shortUrl 短域名
     */
    public static void validateShortUrl(String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)) {
            throw new BusinessException(ErrorInfoEnum.URL_EMPTY_ERROR);
        }
        if (shortUrl.length() > BusinessConstants.SHORTURL_MAX_LENGTH) {
            throw new BusinessException(ErrorInfoEnum.SHORTURL_LENGTH_ERROR);
        }
        if (!shortUrl.contains(BusinessConstants.BASE_URL)) {
            throw new IllegalArgumentException("url is not illegal");
        }
    }
}
