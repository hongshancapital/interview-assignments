package com.getao.urlconverter.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class ConverterUtil {

    // 62进制的字符，包含所有小写字母，大小字母及数字
    private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 要转化成的进制
    private static final int scale = 62;

    // 62进制中的字符
    private static String REGEX = "^[0-9a-zA-Z]+$";

    //短链接长度
    private static final int shortUrlLength = 8;

    /**
     * 10进制数字编码为62进制字符串
     *
     * @param  num 10进制数字
     * @return 62进制字符串
     */
    public static String encode(long num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }

    /**
     * 62进制字符串解码为10进制
     *
     * @param str 编码后的62进制字符串
     * @return 10进制数字
     */
    public static long decode(String str) {
        // 替换0开头的字符串
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }

        return num;
    }

    /**
     * 判断输入的短链接是否合法
     *
     * @param url 短链接
     * @return bool
     */
    public static boolean isLegalShort(String url) {
        return  url.matches(REGEX)&&url.length() == 8;
    }

    /**
     * 判断输入的长链接是否合法
     *
     * @param url 长链接
     * @return bool
     */
    public static boolean isLegalUrl(String url) {
        url = url.toLowerCase();
        String regex= "^((https|http|ftp|rtsp|mms)?://)"  //https、http、ftp、rtsp、mms
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return  url.matches(regex);
    }
}