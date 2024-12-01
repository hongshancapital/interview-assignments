package com.hongshan.interfacejob.validator;

import com.hongshan.interfacejob.constant.CommonConstants;
import org.assertj.core.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class DomainNameValidator {

    public String validateGetShortUrl(String longUrl) {
        String result = "";
        if (Strings.isNullOrEmpty(longUrl)) {
            result = "没有传递longUrl";
        }
        if (longUrl.length() > CommonConstants.MAX_SUPPORT_URL_LENGTH) {
            result = "链接过长,最长不超过"+CommonConstants.MAX_SUPPORT_URL_LENGTH;
        }
        return result;
    }

    public String validateGetLongUrl(String shortUrl) {
        String result = "";
        if (Strings.isNullOrEmpty(shortUrl)) {
            result = "没有传递shortUrl";
        }
        if (shortUrl.length() > CommonConstants.MAX_SHORT_URL_LENGTH) {
            result = "链接过长,最长不超过"+CommonConstants.MAX_SHORT_URL_LENGTH;
        }
        return result;
    }
}
