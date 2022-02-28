package com.demo.shortenurl.util;

public class Utils {
    public final static String EMPTY_STRING = "";
    public final static String DEFAULT_ERROR_SHORTENURL = "zzzzzzzz";
    public final static int SHORTENURL_MAXLENGTH = 8;
    private final static String VALID_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final static int SHORTENURL_SCALE = 62;


    /**
     * 将一个long值转换为其对应的62进制string
     * @param num shorten url 十进制id
     * @return 62进制string
     */
    public static String encodeBy62Decimal(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > Utils.SHORTENURL_SCALE - 1) {
            int remainder = Long.valueOf(num % Utils.SHORTENURL_SCALE).intValue();
            sb.append(Utils.VALID_CHARS.charAt(remainder));
            num = num / Utils.SHORTENURL_SCALE;
        }
        sb.append(Utils.VALID_CHARS.charAt(Long.valueOf(num).intValue()));

        // 由于是从末位开始追加的，因此最后需要反转字符串
        return sb.reverse().toString();
    }

    /**
     * 检查一个短URL是否合法，合法条件：不为空，长度不超过8，且由字母和数字组成
     * @param shortenUrl 短URL string
     * @return 如果满足合法条件，返回true，否则返回false
     */
    public static boolean isShortenUrlValid(String shortenUrl) {
        if (Utils.isStringEmpty(shortenUrl) || shortenUrl.length() > Utils.SHORTENURL_MAXLENGTH) {
            return false;
        }

        for (int i = 0; i < shortenUrl.length(); i++) {
            char c = shortenUrl.charAt(i);
            if (Utils.VALID_CHARS.indexOf(c) == -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查一个string 是否为null，或者为空
     * @param str 输入string
     * @return 满足条件，返回true，否则false
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
