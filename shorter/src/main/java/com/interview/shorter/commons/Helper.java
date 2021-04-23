package com.interview.shorter.commons;

import org.springframework.util.StringUtils;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 * 工具类
 */
public final class Helper {

    private Helper() {

    }

    /**
     * 为了防止碰撞，产生三个hash
     *
     * @param source 原字符
     * @return hash值
     */
    static public long[] hash(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        long[] rlt = new long[3];

        byte[] s = source.getBytes();
        rlt[0] = Hash.longHash(s);
        rlt[1] = Hash.trans(s);
        rlt[2] = Hash.transMirror(s);

        return rlt;
    }


    public static String random(int length, char[] specimen) {
        StringBuffer stringBuffer = new StringBuffer(length);

        int radix = specimen.length - 1;

        for (int i = 0; i < length; i++) {
            int a = (int)Math.round(Math.random() % radix * radix);
            stringBuffer.append(specimen[a]);
        }

        return stringBuffer.toString();
    }
    public final static char[] digits = {
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
    public static String toString(long l, int radix)
    {
        if(radix < 2 || radix > 62) {
            radix = 10;
        }
        if(radix == 10) {
            return Long.toString(l);
        }

        char[] ac = new char[65];
        int j = 64;
        boolean flag = l < 0L;
        if(!flag) {
            l = -l;
        }
        for(; l <= (long)(-radix); l /= radix) {
            ac[j--] = digits[(int)(-(l % (long)radix))];
        }

        ac[j] = digits[(int)(-l)];
        if(flag) {
            ac[--j] = '-';
        }
        return new String(ac, j, 65 - j);
    }
}
