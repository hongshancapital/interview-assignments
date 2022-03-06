package test.utils;

import org.apache.commons.lang3.StringUtils;

public class NumberConvertUtil {

    private static final String[] ELEMENT = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
        "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
        "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 传入一个十进制的数字，返回一个8位字符串
     *
     * @param num 传入的数字
     * @return 8位字符串
     */
    public static String convert(long num) {
        int size = ELEMENT.length;
        StringBuilder shortLink = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            long remainder = num % size;
            long quotient = num / size;
            shortLink.append(ELEMENT[(int) remainder]);
            if (quotient == 0) {
                break;
            }
            num = quotient;
        }

        return StringUtils.reverse(shortLink.toString());
    }

}
