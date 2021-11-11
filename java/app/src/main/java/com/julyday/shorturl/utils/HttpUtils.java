package com.julyday.shorturl.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * http 工具类.
 */
public class HttpUtils {

    public static boolean verifyUrl(String url) {
        if (!StringUtils.hasLength(url)) {
            return false;
        }
        String regEx = "(http|https)://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
