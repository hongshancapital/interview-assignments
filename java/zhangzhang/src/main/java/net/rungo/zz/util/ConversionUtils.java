package net.rungo.zz.util;

public class ConversionUtils {

    /**
     * 十进制转62进制（仅限正整数）
     * @author zifangsky
     * @date 2020/11/13 12:08
     * @since 1.0.0
     * @param num 十进制数字
     * @return java.lang.String
     */
    public static String decimalToSixtyTwo(long num){
        if(num <= 0){
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        //余数
        long remainder;

        while (num > 0){
            remainder = num % 62;

            //0-9
            if(remainder < 10){
                sb.append((char)('0' + remainder));
            }
            //A-Z
            else if(remainder < 36){
                sb.append((char)('A' + remainder - 10));
            }
            //a-z
            else{
                sb.append((char)('a' + remainder - 36));
            }

            num = num / 62;
        }

        //因为在上面的循环过程中，后一次循环本应是计算出来的高位字符，但是却被我们放在字符串的最后面，因此最终结果需要再反转一次
        return sb.reverse().toString();
    }

}
