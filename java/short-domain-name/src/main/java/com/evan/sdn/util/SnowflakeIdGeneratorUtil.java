package com.evan.sdn.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 雪花算法生成分布式唯一ID
 *
 * @author chenyuwen
 * @date 2020/9/9
 */
public class SnowflakeIdGeneratorUtil {

    /**
     * 每毫秒内产生的id数
     */
    private static final long SEQUENCE_BITS = 7L;

    /**
     * 起始时间戳
     * 截止时间 2091-08-19T15:47:35
     */
    private static final long START_TIMESTAMP = LocalDateTime
            .of(2021, 12, 13, 0, 0, 0)
            .toInstant(ZoneOffset.ofHours(8))
            .toEpochMilli();

    private static final String BASE_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ{}";

    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 记录产生时间毫秒数,判断是否是同1毫秒
     */
    private static long lastTimestamp = System.currentTimeMillis();

    /**
     * 代表一毫秒内生成的多个id的最新序号 2进制6位 64个
     */
    private static long sequence;

    public static synchronized String nextId() {
        check();
        //41     7
        //时间戳 序号
        long id = ((lastTimestamp - START_TIMESTAMP) << SEQUENCE_BITS) | sequence;
        StringBuilder sb = new StringBuilder();
        while (id != 0) {
            sb.append(BASE_CHAR.charAt((int) (id & 63)));
            id = id >> 6;
        }
        return sb.toString();
    }

    private static void check() {
        long temp = System.currentTimeMillis();
        sequence = (sequence + 1) & SEQUENCE_MASK;
        if (lastTimestamp > temp) {
            //当某一毫秒的时间,产生的id数超过,系统会进入等待,直到下一毫秒,系统继续产生ID
            if (sequence == 0) {
                lastTimestamp++;
            }
        } else if (lastTimestamp == temp) {
            //当某一毫秒的时间,产生的id数超过,系统会进入等待,直到下一毫秒,系统继续产生ID
            if (sequence == 0) {
                while (lastTimestamp == temp) {
                    lastTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            lastTimestamp = temp;
        }
    }
}
