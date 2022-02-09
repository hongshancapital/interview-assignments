package flypig.url.mapping.util;


/**
 * 10进制与62进制转换工具类
 */
public class Long2StringUtil {


    /**
     * 10进制到62进制
     *
     * @param num
     * @return
     */
    public static String long2String(Long num) {
        if (num == null || num <= 0) {
            throw new IllegalArgumentException("need positive number");
        }

        StringBuilder sb = new StringBuilder();
        //余数
        long remainder;

        while (num > 0) {
            remainder = num % 62;

            //0-9
            if (remainder < 10) {
                sb.append((char) ('0' + remainder));
            }
            //A-Z
            else if (remainder < 36) {
                sb.append((char) ('A' + remainder - 10));
            }
            //a-z
            else {
                sb.append((char) ('a' + remainder - 36));
            }

            num = num / 62;
        }

        //因为在上面的循环过程中，后一次循环本应是计算出来的高位字符，但是却被我们放在字符串的最后面，因此最终结果需要再反转一次
        return sb.reverse().toString();
    }


    /**
     * 62进制到10进制
     *
     * @param numStr
     * @return
     */
    public static long string2long(String numStr) {
        long num = 0;
        int idx;
        for (int i = 0; i < numStr.length(); i++) {
            idx = numStr.charAt(numStr.length() - 1 - i);

            if (idx >= 'a') {
                num += (idx + 36 - 'a') * Math.pow(62, i);
            } else if (idx >= 'A') {
                num += (idx + 10 - 'A') * Math.pow(62, i);
            } else {
                num += (idx - '0') * Math.pow(62, i);
            }
        }

        return num;
    }

}
