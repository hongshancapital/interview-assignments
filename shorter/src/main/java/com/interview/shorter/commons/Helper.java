package com.interview.shorter.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.util.StringUtils;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
/**
 *  工具类
 *
 */
public abstract class Helper {
    /**
     * 为了防止碰撞，产生三个hash
     * @param source
     * @return
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

    /**
     * convert null to string
     * @param s
     * @return
     */
    static public String null2Empty(String s) {
        return s == null ? "" : s;
    }


    /**
     * convert null to Blank
     * @param s
     * @return
     */
    static public String null2Blank(String s) {
        return s == null ? " " : s;
    }

    /**
     * encode url
     * @param source
     * @return
     * @throws UnsupportedEncodingException
     */
    static public String encodeUrl(String source) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(source)) {
            return source;
        }

        if (!source.contains("?")) {
            return source;
        }

        String rlt;
        String query;
        String[] spd = source.split("\\?");
        rlt = spd[0];
        query = spd[1];

        if (StringUtils.isEmpty(query)) {
            return rlt;
        }

        String[] pas = query.split("&");
        rlt += "?";
        for (String p : pas) {
            String[] pv = p.split("=");
            rlt += pv[0] + "=";
            if (pv.length > 1) {
                rlt += URLEncoder.encode(pv[1], "UTF-8");
            }

            rlt += "&";
        }

        return rlt;
    }

    public static String encode_b64(String data) {
        return encode_b64(data.getBytes());
    }

    public static String encode_b64(byte[] toEnc) {
        return encode_b64(toEnc, 0, toEnc.length);
    }

    public static String encode_b64(byte[] data, int off, int len) {
        if (len < 0) {
            len = data.length - off;
        }
        int char_count = 0;
        int bits = 0;
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < len) {
            bits |= data[off] & 0xff;
            if (++char_count == 3) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                                .charAt(bits >> 18 & 0x3f));
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                                .charAt(bits >> 12 & 0x3f));
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                                .charAt(bits >> 6 & 0x3f));
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                                .charAt(bits >> 0 & 0x3f));
                bits = 0;
                char_count = 0;
            } else {
                bits <<= 8;
            }
            i++;
            off++;
        }
        if (char_count != 0) {
            bits <<= 16 - 8 * char_count;
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                    .charAt(bits >> 18 & 0x3f));
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                    .charAt(bits >> 12 & 0x3f));
            char ch = '=';
            if (char_count != 1) {
                ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                        .charAt(bits >> 6 & 0x3f);
            }
            sb.append(ch);
            sb.append('=');
        }
        return sb.toString();
    }

    public static String random(int length, char[] specimen) {
        StringBuffer sb = new StringBuffer(length);

        int radix = specimen.length - 1;

        for (int i = 0; i < length; i++) {
            int a = (int)Math.round(Math.random() % radix * radix);
            sb.append(specimen[a]);
        }

        return sb.toString();
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

        char ac[] = new char[65];
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
