package com.sunjinghao.shorturl.common.util;

/**
 *  Feistel 密码结构
 *  将有序的数字映射为无序
 *
 * @author sunjinghao
 */
public class NumberEncodeUtil {

    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 加密
     * @param number
     * @return
     */
    public static Long encryptNumber(Long number) {
        Long sid = (number & 0xff000000);
        sid += (number & 0x0000ff00) << 8;
        sid += (number & 0x00ff0000) >> 8;
        sid += (number & 0x0000000f) << 4;
        sid += (number & 0x000000f0) >> 4;
        sid ^= 11184810;
        return sid;
    }

    /**
     * 解密
     * @param number
     * @return
     */
    public static Long decodeNumber(Long number) {
        number ^= 11184810;
        Long id = (number & 0xff000000);
        id += (number & 0x00ff0000) >> 8;
        id += (number & 0x0000ff00) << 8;
        id += (number & 0x000000f0) >> 4;
        id += (number & 0x0000000f) << 4;
        return id;
    }


    /**
     * 将十进制的数字转换为指定进制的字符串
     *
     * @param number 十进制的数字
     * @param seed   指定的进制
     * @return 指定进制的字符串
     */
    public static String numberEncryptToStr(long number, int seed) {
        if (number < 0) {
            number = ((long) 2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0) {
            buf[--charPos] = digits[(int) (number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits[(int) (number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 将其它进制的数字（字符串形式）转换为十进制的数字
     *
     * @param number 其它进制的数字（字符串形式）
     * @param seed   指定的进制，也就是参数str的原始进制
     * @return 十进制的数字
     */
    public static long strToNumber(String number, int seed) {
        char[] charBuf = number.toCharArray();
        if (seed == 10) {
            return Long.parseLong(number);
        }

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
        return result;
    }

}

