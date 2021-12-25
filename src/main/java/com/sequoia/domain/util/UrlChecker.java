package com.sequoia.domain.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UrlChecker {
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(new String[]{"http", "https"});

    /**
     * 检验url参数是否合法
     *
     * @param encodedUrl base64编码的url
     */
    public static void check(String encodedUrl) {
        if (StringUtils.isEmpty(encodedUrl)) {
            throw new IllegalArgumentException("url is empty");
        }
        try {
            String decodedUrl = new String(Base64.getDecoder().decode(encodedUrl), StandardCharsets.UTF_8);
            if (!URL_VALIDATOR.isValid(decodedUrl)) {
                throw new IllegalArgumentException("url is invalid");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("URL is not a legal Base64 encoding");
        }
    }
}
