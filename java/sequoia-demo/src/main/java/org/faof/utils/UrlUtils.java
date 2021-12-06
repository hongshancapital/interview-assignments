package org.faof.utils;

public class UrlUtils {

    public static final int SHORT_URL_LENGTH = 8;

    public static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static String hexTo62(long number) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int remainder = (int) (number % 62);
            sBuilder.append(DIGITS[remainder]);
            number = number / 62;
        }
        return sBuilder.reverse().toString();
    }

    public static long _62ToHex(String _62) {
        long value = 0;
        char[] chars = _62.toCharArray();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            value *= 62;
            if (chars[i] >= '0' && chars[i] <= '9') {
                value += chars[i] - '0';
            } else if (chars[i] >= 'a' && chars[i] <= 'z') {
                value += chars[i] - 'a' + 10;
            } else if (chars[i] >= 'A' && chars[i] <= 'Z') {
                value += chars[i] - 'A' + 36;
            }
        }
        return value;
    }

    public static boolean isUrl(String url) {
        url = url.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return url.matches(regex);
    }

}
