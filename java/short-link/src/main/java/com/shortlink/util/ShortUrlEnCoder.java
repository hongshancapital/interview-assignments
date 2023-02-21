package com.shortlink.util;

public class ShortUrlEnCoder {

    public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 将长域名转换为8位的短字符串
     * @param longUrl
     * @return
     */
    public static String encodeLongUrl(String longUrl){
        return encode16bytes(MurmurHashUtil.hash(longUrl));
    }

    /**
     * 将16字节转换为数字+字母的字符串
     * @param hashBytes
     * @return
     */
    public static String encode16bytes(byte[] hashBytes){
        if (hashBytes.length != 16) {
            throw new IllegalArgumentException("待编码字节长度不是16");
        }
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int low = hashBytes[2*i];  // 低位byte，不移动
            int high = (hashBytes[2*i+1] & 0x3f)<<8;  // 高位byte保留右边6位（防止负数），左移8位
            int charIndex = (high + low) % 62;
            shortUrl.append(ALPHABET.charAt(charIndex));
        }

        return shortUrl.toString();
    }
}
