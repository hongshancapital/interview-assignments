package com.scdt.interview.url.utils;

/**
 * @author: lijin
 * @date: 2021年10月09日
 */
public class NumberUtil {

    /**
     * 支持的最小进制
     */
    public static int MIN_RADIX = 2;
    /**
     * 支持的最大进制
     */
    public static int MAX_RADIX = 62;

    /**
     * 锁定创建
     */
    private NumberUtil() {
    }
    /**
     * 0-9a-zA-Z表示62进制内的 0到61。
     */
    private static final String num62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回一字符串，包含 十进制 number 以 radix 进制的表示。
     * @param dec       需要转换的数字
     * @param toRadix    输出进制。当不在转换范围内时，此参数会被设定为 2，以便及时发现。
     * @return    指定输出进制的数字
     */
    public static String dec2Any(long dec, int toRadix) {
        if (toRadix < MIN_RADIX || toRadix > MAX_RADIX) {
            toRadix = 2;
        }
        if (toRadix == 10) {
            return String.valueOf(dec);
        }
        // -Long.MIN_VALUE 转换为 2 进制时长度为65
        char[] buf = new char[65]; //
        int charPos = 64;
        boolean isNegative = (dec < 0);
        if (!isNegative) {
            dec = -dec;
        }
        while (dec <= -toRadix) {
            buf[charPos--] = num62.charAt((int) (-(dec % toRadix)));
            dec = dec / toRadix;
        }
        buf[charPos] = num62.charAt((int) (-dec));
        if (isNegative) {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (65 - charPos));
    }

}
