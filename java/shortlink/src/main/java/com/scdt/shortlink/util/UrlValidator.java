package com.scdt.shortlink.util;

import java.util.regex.Pattern;

public class UrlValidator {
    private static final Pattern URL_PATTERN = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");

    // 校验url是否符合URL规范，可加上域名白名单
    public static boolean valid(String url) {
        return URL_PATTERN.matcher(url).matches();
    }
}
