package com.example.domain.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名生成器
 *
 * @author 雷池
 */
public class ShortDomainGenerator {

    /**
     * 62进制的字符组合
     */
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 唯一序列，每次自增1
     */
    private static final AtomicLong UNIT_SEQUENCE = new AtomicLong();

    /**
     * 最大的序列
     */
    private static final long MAX_SEQUENCE = (long) Math.pow(62, 8);


    /**
     * 生成短域名
     *
     * @return 从00000001开始增加的字符串
     */
    public static String generate() {
        long seq = UNIT_SEQUENCE.incrementAndGet();
        if (seq > MAX_SEQUENCE) {
            throw new RuntimeException("序列已用完");
        }
        char[] seqStr = "00000000".toCharArray();
        for (int i = seqStr.length - 1; i >= 0 && seq != 0; i--) {
            seqStr[i] = CHARS.charAt((int) (seq % 62L));
            seq = seq / 62L;
        }
        return new String(seqStr);
    }
}
