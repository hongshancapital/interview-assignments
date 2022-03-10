package org.calmerzhang.util;

/**
 * 进制转换工具类
 *
 * @author calmerZhang
 * @create 2022/01/14 4:43 下午
 */
public class RadixUtil {
    private static final char[] DIGITS = {'0', 'k', 'l', '1', '5', '6', '7', '8', '9', 'P', 'Q', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'm', 'n', 'o', '2', '3', '4', 'r', 's', 't', 'B','u', 'w', 'x', 'y', 'z', 'p', 'q', 'A', 'C', 'Z', 'E', 'F', 'G', 'H', 'I', 'J', 'U', 'V', 'W', 'M', 'O', 'R', 'S', 'T', 'K', 'L', 'X', 'Y', 'D', 'v', 'N' };

    /**
     * 十进制转62进制
     * @param codeNum
     * @return
     */
    public static String get62String(long codeNum) {
        StringBuilder sBuilder = new StringBuilder();
        while (codeNum != 0) {
            int remainder = (int) (codeNum % 62);
            sBuilder.append(DIGITS[remainder]);
            codeNum = codeNum / 62;
        }
        return sBuilder.reverse().toString();
    }
}
