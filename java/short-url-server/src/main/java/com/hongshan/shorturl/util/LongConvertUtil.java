package com.hongshan.shorturl.util;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
public class LongConvertUtil {

    private static final String SEED = "Lefq7N3Dr4zbXdkycYijnwKx8pv0QthUTB61o9AFVuOHsMJaIlGP2RSWmCEg5Z";

    /**
     * 将10进制的long值 转成62进制的字符串
     *
     * @param num
     * @return {@link String}
     * @throws
     * @date 2022/3/19
     * @author huachengqiang
     */
    public static String convertToStr(long num) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) {
            sb.append(SEED.charAt((int) num));
            return sb.toString();
        }
        while (num > 0) {
            int index = (int) (num % SEED.length());
            sb.append(SEED.charAt(index));
            num /= SEED.length();
        }
        return sb.reverse().toString();
    }
}
