package com.lisi.urlconverter.util;

import java.util.Random;

/**
 * @description: 获取随机数工具类
 * @author: li si
 */
public class RandomUtil {
    private static final char[] ARR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static Character getRandomCharacter(){
        return ARR[RANDOM.nextInt(62)];
    }
}
