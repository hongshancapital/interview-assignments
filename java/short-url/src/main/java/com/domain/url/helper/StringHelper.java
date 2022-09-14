package com.domain.url.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringHelper {
    /**
     * 验证字符串是否是URL
     *
     * @param s 字符串
     * @return true: 是，false: 否
     */
    public static boolean isUrl(String s) {
        if (s == null) {
            return false;
        }
        String regex = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }
}
