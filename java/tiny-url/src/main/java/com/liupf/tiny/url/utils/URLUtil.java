package com.liupf.tiny.url.utils;

import java.util.regex.Pattern;

public final class URLUtil {

    private static final Pattern URL_PATTERN =
            Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");


    public static Boolean isUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

}
