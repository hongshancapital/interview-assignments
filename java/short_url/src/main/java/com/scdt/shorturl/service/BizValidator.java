package com.scdt.shorturl.service;


import com.scdt.shorturl.exception.BizInValidException;
import com.scdt.shorturl.exception.ExceptionMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 题目要求不准用Hibernate-validation-api
 * 用此类代替Validator的实现
 */
@Component
public class BizValidator {
    /**
     * 长域名正则表达式
     */
    private static final Pattern LONG_URL_PATTERN = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    /**
     * 短域名正则表达式
     */
    private static final Pattern SHORT_URL_PATTERN = Pattern.compile("[a-zA-Z0-9]{8}");

    /**
     * 短域名长度最大为8个字符
     */
//    public static final int SHORT_URL_ID_MAX_BYTE_LENGTH = 8;
    @Value("${scdt.short-url-id-max-byte-length}")
    private int SHORT_URL_ID_MAX_BYTE_LENGTH;
    /**
     * 1个长域名映射记录(UTF-8)限制1KB
     */
//    public static final int LONG_URL_MAX_BYTE_LENGTH = (1 << 10) - SHORT_URL_ID_MAX_BYTE_LENGTH;
    @Value("${scdt.long-url-max-byte-length}")
    private int LONG_URL_MAX_BYTE_LENGTH;


    public void validLongUrl(Set<String> longUrls) {
        if (longUrls != null && !longUrls.isEmpty()) {
            for (String longUrl : longUrls) {
                if (longUrl.getBytes(StandardCharsets.UTF_8).length > LONG_URL_MAX_BYTE_LENGTH) {
                    throw new BizInValidException(ExceptionMessage.URL_LENGTH_INVALID, longUrl);
                }
                if (!LONG_URL_PATTERN.matcher(longUrl).matches()) {
                    throw new BizInValidException(ExceptionMessage.INCORRECT_URL, longUrl);
                }
            }
        } else {
            throw new BizInValidException(ExceptionMessage.NOT_NULL);
        }
    }

    public void validShortUrl(Set<String> shortUrls) {
        if (shortUrls != null && !shortUrls.isEmpty()) {
            for (String shortUrl : shortUrls) {
                if (shortUrl.getBytes(StandardCharsets.UTF_8).length != SHORT_URL_ID_MAX_BYTE_LENGTH) {
                    throw new BizInValidException(ExceptionMessage.SHORT_URL_LENGTH_INVALID, shortUrl);
                }
                if (!SHORT_URL_PATTERN.matcher(shortUrl).matches()) {
                    throw new BizInValidException(ExceptionMessage.INCORRECT_URL, shortUrl);
                }
            }
        } else {
            throw new BizInValidException(ExceptionMessage.NOT_NULL);
        }

    }
}
