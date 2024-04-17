package com.sequoia.infrastructure.util;

import com.sequoia.infrastructure.common.exception.TinyCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * HexUtil：进制转换工具类
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Slf4j
public class HexUtil {

    private static final int BASE = Constant.BASE_DIGITS.length();

    /**
     * 通过余数获取对应的64进制表示
     */
    private static final char[] DIGITS_CHAR = Constant.BASE_DIGITS.toCharArray();

    private static int checkNum = 10;

    /**
     * 这里预留了足够的空位122位
     */
    private static final int FAST_SIZE = 'z';

    /**
     * 这个是为了存放字母对应的值，比如-对应63，但是-是45，也就是 digitsIndex[45]=63
     *   [digitsChar[-]会自动转变成45，这样子十六进制转十进制，就可以获取到前面的数字了。
     */
    private static final int[] DIGITS_INDEX = new int[FAST_SIZE + 1];
    static {
        for (int i = 0; i < FAST_SIZE; i++) {
            DIGITS_INDEX[i] = -1;
        }
        //构造：a[117]=30这样的数组
        for (int i = 0; i < BASE; i++) {
            DIGITS_INDEX[DIGITS_CHAR[i]] = i;
        }
    }

    /**
     * 62进制转十进制
     * @param s
     * @return
     */
    public static long hex62To10(String s) {
        long result = 0L;
        long multiplier = 1;
        for (int pos = s.length() - 1; pos >= 0; pos--) {
            int index = getIndex(s, pos);
            result += index * multiplier;
            multiplier *= BASE;
        }
        return result;
    }

    /**
     * 十进制转62进制
     * @param number
     * @return
     */
    public static String hex10To62(long number) {
        return hexByBase(number, BASE);
    }

    private static String hexByBase(long origin, int base) {
        long num = 0;
        if (origin < 0) {
            num = ((long)2 * 0x7fffffff) + origin + 2;
        } else {
            num = origin;
        }

        char[] buf = new char[32];
        int charPos = 32;
        while ((num / base) > 0) {
            buf[--charPos] = DIGITS_CHAR[(int)(num % base)];
            num /= base;
        }
        buf[--charPos] = DIGITS_CHAR[(int)(num % base)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 获取对应的的64进制的值
     */
    private static int getIndex(String s, int pos) {
        char c = s.charAt(pos);
        if (c > FAST_SIZE) {
            log.error("Unknow character for Base64: {}", s);
            throw new TinyCodeException("短链接中含有非法字符");
        }
        return DIGITS_INDEX[c];
    }

    /**
     * 计算校验位
     * @param code
     * @return
     */
    public static char getCheckCode(String code) {
        long unm = hex62To10(code)+ checkNum;
        int a = (int) (unm % BASE);
        return DIGITS_CHAR[a];
    }

    /**
     * 随机生成一位
     * @return
     */
    public static char getRandomHex62Char() {
        int index = ThreadLocalRandom.current().nextInt(BASE);
        return DIGITS_CHAR[index];
    }

    /**
     * 生成指定 bitCount 位的62进制对应的最大 十进制数
     * @param bitCount
     * @return
     */
    public static long maxHex62(int bitCount) {
        String hex62 = StringUtils.repeat(Constant.BASE_DIGITS_MAX, bitCount);
        return hex62To10(hex62);
    }

}
