package com.bolord.shorturl.utils;

import java.util.regex.Pattern;

/**
 * URL 工具类
 */
public class UrlUtils {

    private static final Pattern pattern = Pattern.compile(
        "^(((https?)://)" +
        "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?#:@&=+$,A-Za-z0-9])+)" +
        "([).!';/?:,][[:blank:]])?$"
    );

    public static boolean isValidUrl(String url) {
        return pattern.matcher(url).matches();
    }

}
