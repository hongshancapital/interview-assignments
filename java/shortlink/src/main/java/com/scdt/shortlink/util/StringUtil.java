package com.scdt.shortlink.util;

public class StringUtil {
    public static boolean isNotEmpty(String str) {
        return null != str && str.trim().length() > 0;
    }
}
