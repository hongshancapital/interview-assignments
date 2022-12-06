package com.domain.common;

import java.util.Random;

/**
 * @author: xielongfei
 * @date: 2022/01/09
 * @description: 转换字符串工具类
 */
public class ConvertTool {

    public static final String domain_prefix = "http://do.com/";

    /**
     * 参考Integer.toString();
     * {@link Integer#toString(int, int)}
     */

    static final char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z' ,
            'A' , 'B' , 'C' , 'D' , 'E' , 'F' ,
            'G' , 'H' , 'I' , 'J' , 'K' , 'L' ,
            'M' , 'N' , 'O' , 'P' , 'Q' , 'R' ,
            'S' , 'T' , 'U' , 'V' , 'W' , 'X' ,
            'Y' , 'Z'
    };

    /**
     * hashcode转string
     * @param i
     * @return
     */
    public static String toString(int i) {
        int radix = 61;
        //存储转换进制后字符数组
        char[] buf = new char[12];
        boolean negative = (i < 0);
        //将指针指向buf数组的最后一位，以便后边存放字符
        int charPos = 11;

        if (!negative) {
            i = -i;
        }

        //使用短除法来转换进制
        while (i <= -radix) {
            buf[charPos--] = digits[-(i % radix)];
            i = i / radix;
        }
        buf[charPos] = digits[-i];

        if (negative) {
            buf[--charPos] = '-';
        }
        //不够8字符，随机补全，减少字符串冲突概率
        while (charPos > 4) {
            buf[--charPos] = digits[new Random().nextInt(61)];
        }

        return String.valueOf(buf, charPos, 8);
    }

    /**
     * 计算字符串
     * @param s
     * @return
     */
    public static String toString(String s){
        int hash = Math.abs(s.hashCode());
        return toString(hash);
    }

}
