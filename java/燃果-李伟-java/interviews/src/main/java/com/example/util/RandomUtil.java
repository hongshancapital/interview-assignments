package com.example.util;

import java.util.UUID;

/**
 * 随机生成工具类
 * @author KyrieCao
 * @date 2020/2/5 23:05
 */
public class RandomUtil {

    /**
     * 生成32位UUID
     * @return String
     * @author KyrieCao
     * @date 2020/2/5 23:05
     */
    public static String UUID32() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }

}
