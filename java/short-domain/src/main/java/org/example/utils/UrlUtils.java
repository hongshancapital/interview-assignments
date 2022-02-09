package org.example.utils;

import org.springframework.util.StringUtils;

public class UrlUtils {
    public static boolean checkUrl(String url) {
        if(StringUtils.isEmpty(url)) {
            return false;
        }

        String regex = "(https|http)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        return url.matches(regex);
    }
}
