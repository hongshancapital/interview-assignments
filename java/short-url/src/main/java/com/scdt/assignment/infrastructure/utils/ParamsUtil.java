package com.scdt.assignment.infrastructure.utils;

/**
 * 参数工具类型
 */
public class ParamsUtil {

    public static String validUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("输入参数不合法");
        }
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        return url;
    }
}
