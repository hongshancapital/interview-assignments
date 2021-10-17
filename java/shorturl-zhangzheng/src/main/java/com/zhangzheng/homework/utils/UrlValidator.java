package com.zhangzheng.homework.utils;

import java.util.regex.Pattern;

/**
 * @ClassName Validator
 * @Description: 验证工具类
 * @Author gravel
 * @Date 2020/2/10
 * @Version V1.0
 **/
public class UrlValidator {

    private final static String URL_REGEX = "(ftp|http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";

    /**
     * 验证URL地址
     *
     * @param url
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkUrl(String url) {
        return Pattern.matches(URL_REGEX, url);
    }
}
