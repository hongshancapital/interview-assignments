package com.cx.shorturl.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {

    /**
     * 校验URL
     * @param url
     * @return
     */
    public static boolean verifyUrl(String url) {
        // URL验证规则
        String regEx = "(http|https)://[^\\s]*";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(url);
        boolean rs = matcher.matches();
        return rs;

    }
}
