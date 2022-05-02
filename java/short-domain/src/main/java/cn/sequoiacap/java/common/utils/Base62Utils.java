package cn.sequoiacap.java.common.utils;

/**
 * 62进制编码(8位可以表示218340105584895个)
 */
public class Base62Utils {
    //使用乱序，防止恶意按顺序攻击
    private static final String BASE_62_STR ="0jklc7NZdeJSfi16abXYOgh45DEPQR3FrsvC2KtuLMTUVWmnoGHI89ABqpwxyz";
    private static final char[] BASE_62_TABLE =BASE_62_STR.toCharArray();
    private static final int SCALE = 62;

    /**
     * 将10进制数字转换成62进制编码
     * @param num 10进制数字
     * @return 62进制编码
     */
    public static String encodeStr(long num) {
        StringBuilder sb = new StringBuilder();

        long remainder = 0;
        do {
            remainder = num % SCALE;
            sb.append(BASE_62_TABLE[(int) remainder]);
            num = num / SCALE;
        }
        while (num > SCALE - 1);

        if (num > 0) {
            sb.append(BASE_62_TABLE[(int) num]);
        }

        return sb.reverse().toString();
    }

    /**
     * 将62进制编码转换成10进制数字
     * @param str 62进制编码
     * @return 10进制数字
     */
    public static long decodeNum(String str) {
        if (!isBase62(str)) {
            return -1;
        }

        long num = 0;
        int index = 0;
        while (str.length() > 1) {
            index = BASE_62_STR.indexOf(str.substring(0, 1));
            num += (long)(index * (Math.pow(SCALE, str.length() - 1)));
            str = str.substring(1);
        }
        index = BASE_62_STR.indexOf(str.substring(0, 1));
        num += (long)index;

        return num;
    }

    /**
     * 判断是否为62进制编码
     * @param str 62进制编码
     * @return 10进制数字
     */
    public static boolean isBase62(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return str.matches("([0-9a-zA-Z])+");
    }

}
