package com.example.assignment.utils;

import java.util.regex.Pattern;

public class MatchUtil {

    private static String URL_PATTERN = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static String SHORT_CODE_PATTERN = "^[0-9a-zA-Z]{1,11}$"; //长整型数转62进制最大不会超过11位

    public static boolean isUrlLegal(String url) {
        return Pattern.matches(URL_PATTERN, url);
    }

    public static boolean isShortCodeLegal(String shortCode) {
        return Pattern.matches(SHORT_CODE_PATTERN, shortCode);
    }
}
