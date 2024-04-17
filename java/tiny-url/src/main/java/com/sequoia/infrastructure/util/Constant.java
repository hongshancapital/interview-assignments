package com.sequoia.infrastructure.util;

/**
 * Constant: 常量配置
 *
 * @author KVLT
 * @date 2022-04-09.
 */
public class Constant {

    /**
     * 随机短码的最大长度(1位随机位 + 随机码)
     */
    public static Integer TINYURL_MAX_LENGTH = 8;

    /**
     * 62进制可用的字符串(26小写+26大写+10数字)
     */
    public static final String BASE_DIGITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String BASE_DIGITS_MAX = "Z";
    public static final char[] DIGITS_CHAR_ARR = BASE_DIGITS.toCharArray();

    /**
     * 最大的 TINYURL_MAX_LENGTH 位62进制对应的 十进制数
     */
    public static final long HEX62_MAX_LONG = HexUtil.maxHex62(TINYURL_MAX_LENGTH - 1);

    /**
     * 7位62进制下，首位为1 的long， 10_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000
     *   用来将短码二进制首位置1
     */
    public static final long LONG_BITS_HEAD_1 = Long.highestOneBit(HEX62_MAX_LONG);

    /**
     * 7位62进制下，bit全为1 的long， 11_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111
     *  用来控制生成的短码长度
     */
    public static final long LONG_BITS_ALL_1 = (LONG_BITS_HEAD_1 << 1) - 1;

}
