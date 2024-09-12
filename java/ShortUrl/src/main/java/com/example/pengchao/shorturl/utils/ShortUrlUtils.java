package com.example.pengchao.shorturl.utils;

import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

/**
 * 短url的工具类
 *
 * @author pengchao04
 * @date 2022/5/23 11:13
 */
@Slf4j
public class ShortUrlUtils {

    /**
     * 内部采用自增ID的方式，实现 id -> url 的映射（采用数据库自增ID，或分布式ID）
     */
    private static AtomicLong sequence = new AtomicLong(0);

    /**
     * 链接中对应的字符
     */
    private static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 通过长url，生成对应的短字符
     * @param longUrl
     * @return
     */
    public static String generateShortenUrl(String longUrl) {
        long mySeq = sequence.incrementAndGet();
        String shortUrl = to62RadixString(mySeq);
        return shortUrl;
    }

    /**
     * 将对应的ID，转化为62进制的字符串
     *
     * @param seq
     * @return
     */
    public static String to62RadixString(long seq) {
        StringBuilder sBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return sBuilder.toString();
    }


}
