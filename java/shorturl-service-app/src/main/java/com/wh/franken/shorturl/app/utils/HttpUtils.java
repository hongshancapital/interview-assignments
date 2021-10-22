package com.wh.franken.shorturl.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fanliang
 */
public class HttpUtils {

    private HttpUtils() {}

    /**
     * 验证是否是URL
     *
     * @param url 链接
     * @return 判断结果
     */
    public static boolean verifyUrl(String url) {

        // URL验证规则
        String regEx = "(http|https)://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Matcher matcher = pattern.matcher(url);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }
}
