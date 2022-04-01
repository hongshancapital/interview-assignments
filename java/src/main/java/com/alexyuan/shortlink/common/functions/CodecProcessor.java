package com.alexyuan.shortlink.common.functions;

import com.alexyuan.shortlink.exceptions.ImproperValueException;
import org.apache.commons.lang3.StringUtils;

public class CodecProcessor {

    //初始化62进制的数据, 每个index即为char数组内对应的字符的值. E.g. Z = 35, Z = 61. 用来获取62进制当前位置字符
    private static String CHAR_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //合法性验证正则表达式
    private static String VALID_REGEX = "^[0-9a-zA-Z]+$";

    /**
     * @param decimal_val       需要进行转换的原始十进制数据。
     * @param target_length     62进制数目标长度
     * @return                  62进制结果字符串
     * @description             由于是求余转换，在求余过程中是从末位开始计算的，故最终需要进行一次Reverse来获取结果。
     **/
    public static String encodeFromDecimal(long decimal_val, int target_length, int scale) {
        if (scale < 2) {
            throw new ImproperValueException("Config Scale [" + scale + "] is Invalid, Please Check.");
        }

        StringBuilder sb = new StringBuilder();
        int remainder;
        while (decimal_val > scale - 1) {
            remainder = Long.valueOf(decimal_val % scale).intValue();
            sb.append(CHAR_LIST.charAt(remainder));
            decimal_val = decimal_val / scale;
        }
        if (decimal_val < 0) {
            throw new ImproperValueException("Input decimal_val [" + decimal_val + "] is Invalid, Please Check.");
        }
        sb.append(CHAR_LIST.charAt(Long.valueOf(decimal_val).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, target_length, '0');
    }

    /**
     * @param encoded_val       需要进行还原的62进制数据。
     * @return                  10进制原始数字。
     * @description             计算前需现针对输入String进行检查，如存在不合法字符的情况（正则无法匹配），可抛出异常
     *                          62进制转换十进制需要逐位叠加, 每次使用当前字符对应的十进制数字乘上当前位数获得十进制数据。
     **/
    public static long decodeToDecimal(String encoded_val, int scale) {
        if (!encoded_val.matches(VALID_REGEX)) {
            throw new ImproperValueException("Input short-link url is not valid. Please Check.");
        }
        if (scale < 2) {
            throw new ImproperValueException("Config Scale [" + scale + "] is Invalid, Please Check.");
        }
        // 去除字符串先导0
        encoded_val = encoded_val.replaceFirst("^0*", "");
        long decimal_val = 0;
        int single_decimal_value = 0;
        for (int i = 0; i < encoded_val.length(); i++) {
            single_decimal_value = CHAR_LIST.indexOf(encoded_val.charAt(i));
            decimal_val += (long) (single_decimal_value * Math.pow(scale, encoded_val.length() - i - 1));
        }
        return decimal_val;
    }
}
