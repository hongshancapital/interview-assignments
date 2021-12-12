package com.scdt.assignment.infrastructure.utils;


import org.springframework.lang.NonNull;

/**
 * 进制转化工具类 10进制 转化成 {@link NumericConvertUtil.digits.length}
 */
public class NumericConvertUtil {

    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 输出是long 类型， 最大范围 2^64 全部能表达全部的数据
     * 那对应的  {@link NumericConvertUtil.digits.length} 进制 62^11 就可以表达long 的全部数据了。
     */
    private static final char[] CHAR_BUF = new char[15];
    /**
     * 将十进制的数字转换为指定进制的字符串
     * @param number 十进制的数字
     * @return 指定进制的字符串
     */
    public static String toOtherNumber(long number) {
        int seed = digits.length;
        if (number < 0) {
            number = ((long) 2 * 0x7fffffff) + number + 2;
        }

        int charPos = CHAR_BUF.length;
        while ((number / seed) > 0) {
            CHAR_BUF[--charPos] = digits[(int) (number % seed)];
            number /= seed;
        }
        CHAR_BUF[--charPos] = digits[(int) (number % seed)];
        return new String(CHAR_BUF, charPos, (CHAR_BUF.length - charPos));
    }

    /**
     * 将其它进制的数字（字符串形式）转换为十进制的数字
     * @param number 其它进制的数字（字符串形式） 不允许为空
     * @return 十进制的数字
     */
    public static long toDecimalNumber(@NonNull String number) {
        if (number.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int seed = digits.length;
        char[] charBuf = number.toCharArray();
        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i--) {
            int index = 0;
            for (int j = 0, length = digits.length; j < length; j++) {
	            //找到对应字符的下标，对应的下标才是具体的数值
                if (digits[j] == charBuf[i]) {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        if (result < 0) {
            throw new NumberFormatException(String.format("String value %s exceeds " +
                    "range of unsigned long.", result));
        }
        return result;
    }
}