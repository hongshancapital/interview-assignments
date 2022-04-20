package com.shortlink.handler;

import com.shortlink.common.GeneralException;
import com.shortlink.common.Status;
import org.springframework.stereotype.Service;

/**
 * 号码生成器
 */
@Service
public class NumberGeneratorService {

    /** 起始的时间戳 **/
    private final static long START_TIMESTAMP = 1480166465631L;
    /** 每一部分占用的位数 **/
    private final static long SEQUENCE_BIT = 12;   //序列号占用的位数
    private final static long MACHINE_BIT = 5;     //机器标识占用的位数
    private final static long DATA_CENTER_BIT = 5; //数据中心占用的位数
    /** 每一部分的最大值 **/
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    /** 每一部分向左的位移 **/
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = MACHINE_BIT + DATA_CENTER_BIT;
    // 机房ID
    private long dataCenterId = 2;
    // 机器ID
    private long machineId = 3;
    // 序列号 分布式场景下 由redis提供
    private volatile long sequence = 0L;
    // 上一次时间戳
    private volatile long lastTimeStamp = -1L;


    /**
     * 产生下一个ID
     * @return
     */
    public synchronized long nextId() {

        long currTimeStamp = getNewTimeStamp();
        if (currTimeStamp < lastTimeStamp) {
            // 当前时间小于上一次时间戳 直接抛异常 时针回调
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (currTimeStamp == lastTimeStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimeStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastTimeStamp = currTimeStamp;
        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分
                | dataCenterId << DATA_CENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewTimeStamp();
        while (mill <= lastTimeStamp) {
            mill = getNewTimeStamp();
        }
        return mill;
    }

    private long getNewTimeStamp() {
        return System.currentTimeMillis();
    }
}
