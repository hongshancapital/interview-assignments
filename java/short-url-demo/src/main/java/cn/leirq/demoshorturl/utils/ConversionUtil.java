package cn.leirq.demoshorturl.utils;


import java.util.Random;
import org.apache.commons.lang3.StringUtils;

/**
 * 10进制转62进制工具
 *
 * @author Ricky
 */
public class ConversionUtil {

    /**
     * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61等
     */
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;
    private static Random random = new Random();

    /**
     * 获取指定长度的随机字符传
     *
     * @return
     */
    public static String getRandomStr(int length) {
        String str = "";
        str += chars.charAt(random.nextInt(scale));
        return str;
    }

    /**
     * 将数字转为62进制
     *
     * @param num    Long 型数字
     * @param length 转换后的字符串长度，不足则左侧补0
     * @return 62进制字符串
     */
    public static String to62HEX(long num, int length) {
        num = Math.abs(num);
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }

        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }

}
