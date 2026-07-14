/**
 * Project: scdt-sdn
 * File created at 2022/3/13 23:54
 * Copyright (c) 2018 linklogis.com all rights reserved.
 */
package com.scdt.sdn.util;

import org.springframework.stereotype.Component;

/**
 * 功能描述
 *
 * @author donghang
 * @version 1.0
 * @type EncodeUtil
 * @date 2022/3/13 23:54
 */

@Component
public class EncodeUtil {
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int scale = 62;

    //数字转62进制
    public String encode(long num) {
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
        return value;
    }

    //62进制转为数字
    public long decode(String str) {
        //将 0 开头的字符串进行替换
        str = str.replace("^0*", "");
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < str.length(); i++) {
            //获取字符
            tempChar = str.charAt(i);
            //单字符值
            tempCharValue = chars.indexOf(tempChar);
            //单字符值在进制规则下表示的值
            value += (long)(tempCharValue * Math.pow(scale, str.length() - i - 1));
        }
        return value;
    }
}
