package com.heyenan.shorturldemo.strategy;

import cn.hutool.core.lang.hash.MurmurHash;

/**
 * @author heyenan
 * @description Hash策略具体实现类
 *
 * @date 2020/5/07
 */
public class HashStrategy implements ShortUrlStrategy {

    /** 乱序（混淆） */
    private static char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static int SIZE = CHARS.length;

    /**
     * 获取短连接
     *
     * @param longUrl 长链接
     * @return 短链接
     */
    @Override
    public String getShortUrl(String longUrl) {
        int i = MurmurHash.hash32(longUrl);
        long num = i < 0 ? Integer.MAX_VALUE - (long) i : i;
        return convertDecToBase62(num);
    }

    private static String convertDecToBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = (int) (num % SIZE);
            sb.append(CHARS[i]);
            num /= SIZE;
        }
        return sb.reverse().toString();
    }
}
