package interview.shorturl.util;

/**
 * 数据转换
 *
 * @author: ZOUFANQI
 **/
public abstract class ConvertUtil {
    public static void main(String[] args) {
        System.out.println(ConvertUtil.encode10To62(99999999999999L));
    }

    /**
     * 十进制转62进制（正整数）
     *
     * @param num 十进制数字
     * @return 62进制
     */
    public static String encode10To62(long num) {
        if (num <= 0) {
            return "0";
        }
        final StringBuilder resultBuilder = new StringBuilder();
        // 余数
        long remainder;
        while (num > 0) {
            remainder = num % 62;
            // 0-9
            if (remainder < 10) {
                resultBuilder.append((char) ('0' + remainder));
            }
            // A-Z
            else if (remainder < 36) {
                resultBuilder.append((char) ('A' + remainder - 10));
            }
            // a-z
            else {
                resultBuilder.append((char) ('a' + remainder - 36));
            }
            num = num / 62;
        }
        return resultBuilder.reverse().toString();
    }
}
