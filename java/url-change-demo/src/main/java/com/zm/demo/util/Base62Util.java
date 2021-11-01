package com.zm.demo.util;

/**
 * @ClassName Base62Util
 * @Description 10进制与62进制转换工具类
 * @Author zhaomin
 * @Date 2021/10/29 17:40
 **/
public class Base62Util {

    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;

    public static String base62Encode(long num) {
        StringBuilder sBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (num % 62);
            sBuilder.append(chars.charAt(remainder));
            num = num / 62;
            if (num == 0) {
                break;
            }
        }
        return sBuilder.reverse().toString();
    }

    public static long base62Decode(String str) {
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }

}
