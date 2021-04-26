package com.wxp.util;

/**
 * 雪花算法snowflake修改版
 *
 * @author wxp
 **/
public class SnowFlake {

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1619331864000L; // 2021年4月25日 14时24分24秒

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 6; //序列号占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long TIMESTMP_LEFT = SEQUENCE_BIT;

    private static long sequence = 0L; //序列号
    private static long lastStmp = -1L;//上一次时间戳

    public SnowFlake() {

    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized static long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | sequence;                             //序列号部分
    }

    private static long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private static long getNewstmp() {
        return System.currentTimeMillis();
    }

}