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
}