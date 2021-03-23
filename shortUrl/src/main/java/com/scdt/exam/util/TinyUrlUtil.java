package com.scdt.exam.util;


import org.apache.commons.codec.digest.DigestUtils;

public class TinyUrlUtil {
    static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 长链接缩短为短码
     *
     * @param longUrl 长链接
     * @param length  短码长度
     * @return 短码
     */
    public static String[] shortUrl(String longUrl, Integer length) {
        String[] result = new String[4];
        String md5Hex = DigestUtils.md5Hex(longUrl);
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String subString = md5Hex.substring(i * 8, (i + 1) * 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(subString, 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < length; j++) {
                long index = 0x0000003D & lHexLong;
                outChars.append(DIGITS[(int) index]);
                lHexLong = lHexLong >> length;
            }
            String shortUrl = outChars.toString();
            result[i] = shortUrl;
        }
        return result;
    }

}
