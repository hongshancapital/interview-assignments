package com.zhouhongbing.shorturl.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

public class Base62Util {
    /**
     * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61等
     */
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;

    /**
     * 将数字转为62进制
     *
     * @param num    Long 型数字
     * @param length 转换后的字符串长度，不足则左侧补0
     * @return 62进制字符串
     */
    public static String encode(long num, int length) {
        StringBuffer sf = new StringBuffer();
        int remainder = 0;

        while (num > scale - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale).intValue();
            sf.append(chars.charAt(remainder));

            num = num / scale;
        }

        sf.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sf.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
//        return StringUtils.leftPad(value, length);
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode(String str) {
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }

        return num;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        Base64 base64 = new Base64();
        long l = new IdWorker().nextId();
        System.out.println("id:" + l);
        String encodeStr = encode(l, 11);
//        System.out.println("62进制：" + encode(encode(999999999999999999L, 11));
        int length = encodeStr.length();
        System.out.println("62进制：" + encodeStr);
        if ((length <= 8)) {
            System.out.println("62进制：" + encodeStr);
        }
        String substring = encodeStr.substring(length - 8, length);
        System.out.println("62进制：" + substring);
        System.out.println("10进制：" + decode(encodeStr));
    }
}
