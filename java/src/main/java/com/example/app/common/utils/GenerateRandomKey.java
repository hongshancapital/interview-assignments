package com.example.app.common.utils;

import com.example.app.common.exception.ModuleException;

import java.util.Random;

/**
 * 随机ID生成工具类
 *
 * @author voidm
 * @date 2021/9/18
 */
public class GenerateRandomKey {


    public static final char[] CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int scale = 62;


    /**
     * 随机生成指定长度的字符串
     *
     * @param len
     * @return
     * @throws ModuleException
     */
    public static String generateRandomKey(int len) throws ModuleException {
        if (len < 0) {
            throw new ModuleException("长度必须大于 0");
        }

        StringBuilder keyBuffer = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            keyBuffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return keyBuffer.toString();
    }

    /**
     * 将 10 进制数字转化成 62 进制
     *
     * @param num
     * @return
     */
    public static String encode62(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1) {
            // 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            // 除以进制数，获取下一个末尾数
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        return sb.reverse().toString();
    }
}