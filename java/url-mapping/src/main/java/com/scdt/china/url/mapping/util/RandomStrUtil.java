package com.scdt.china.url.mapping.util;

import java.util.Random;

public class RandomStrUtil {

    /**
     *
     */
    private final static String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 随机生成 length 位字符
     * @param length
     * @return
     */
    public static String getRandomStr(int length){
        //定义一个空字符串
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            //随机生成字符
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
