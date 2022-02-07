package com.xg.shorturl.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * url校验
 * @author xionggen
 */
public class UrlValidator {
    private static String regex = "https?://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    public static boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
