package com.rad.shortdomainname.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: 进制转化器
 * @date 2022-03-19 17:41:19
 */
public class NumberConvertUtil {
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static String encode(long num, int scale, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            //除以进制数，获取下一个末尾数
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }
}
