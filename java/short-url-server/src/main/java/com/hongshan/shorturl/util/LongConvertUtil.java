package com.hongshan.shorturl.util;

/**
 * @author: huachengqiang
 * @date: 2022/1/15
 * @description:
 */
public class LongConvertUtil {
    private static final String SEED = "Lefq7N3Dr4zbXdkycYijnwKx8pv0QthUTB61o9AFVuOHsMJaIlGP2RSWmCEg5Z";
    private static final char[] CHAR_DICT = SEED.toCharArray();

    public static String convert(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, CHAR_DICT[(int) (num % CHAR_DICT.length)]);
            num /= CHAR_DICT.length;
        }
        return sb.toString();
    }
}
