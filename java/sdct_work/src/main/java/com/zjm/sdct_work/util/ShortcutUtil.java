package com.zjm.sdct_work.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Author:   billzzzhang
 * Date:     2022/3/19 下午5:11
 * Desc:
 */
//便于测试
@Component
public class ShortcutUtil {

    public static final String ShortcutDomain = "https://shortcut.cn/";
    public static final Integer ShortcutLength = 8;


    public static final Long MAX_SHORT_CUT_LONG = Double.valueOf(Math.pow(2, 47)).longValue() - 1;

    public static final char[] array =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                    'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                    'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
                    'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

    public static String covertLong2Str(long number, int decimal) {
        StringBuilder builder = new StringBuilder();
        while (number != 0) {
            builder.append(array[(int) (number - (number / decimal) * decimal)]);
            number /= decimal;
        }
        return builder.reverse().toString();
    }


    public String generatorRandomStr() {

        Long id = UUID.randomUUID().getMostSignificantBits() & MAX_SHORT_CUT_LONG;
        String shortcut = covertLong2Str(id, 62);
        return shortcut;
    }


    public String getRealShortcutUrl(String shortcut) {
        return ShortcutDomain + shortcut;
    }


}
