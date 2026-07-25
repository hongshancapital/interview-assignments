package com.scdt.shortlink.core.engine;

import java.util.Random;

/**
 * 短链生成引擎
 *
 * @Author tzf
 * @Date 2022/4/29
 */
public class ShortLinkEngine {
    /**
     * 字典表
     */
    private static final String[] dicts = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
        "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
        "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
        "X", "Y", "Z"};

    /**
     * 随机数
     */
    private static Random random = new Random();

    /**
     * 根据入参的位数生成对应的短码
     *
     * @param count 短码长度
     * @return
     */
    public static String createShortLink(int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(dicts[random.nextInt(dicts.length)]);
        }
        return result.toString();
    }

}
