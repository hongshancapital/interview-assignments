package com.check;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

/**
 * @Author jeffrey
 * @Date 2021/10/12
 * @description: 数据校验
 */
@Component
public class DomainNameValidator {

    public static final int MAX_SUPPORT_URL_LENGTH = 1000;
    public static final String BASE_URL = "https://o.cn";
    public void validateGetShortUrl(String longUrl) {
        if (Strings.isNullOrEmpty(longUrl)) {
            throw new IllegalArgumentException("url is null or empty");
        }
        if (longUrl.length() > MAX_SUPPORT_URL_LENGTH) {
            throw new IllegalArgumentException("max support url length is: " + MAX_SUPPORT_URL_LENGTH);
        }
    }

    public void validateGetLongUrl(String shortUrl) {
        if (Strings.isNullOrEmpty(shortUrl)) {
            throw new IllegalArgumentException("url is null or empty");
        }
        String[] splitString = shortUrl.split("/");
        if (splitString.length != 4) {
            throw new IllegalArgumentException("url is not illegal");
        }
        if (!shortUrl.contains(BASE_URL)) {
            throw new IllegalArgumentException("url is not illegal");
        }
    }

}
