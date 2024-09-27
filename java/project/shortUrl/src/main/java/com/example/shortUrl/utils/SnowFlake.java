package com.example.shortUrl.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author HOPE
 * @Description 雪花算法变形，这里省去机房数，构造为48位bit的long型数值
 * @Date 2022/4/28 17:24
 */
public class SnowFlake {
    /** 开始时间截,精确到毫秒，41bit */
    private final long epoch = 1651138822827L;

    /** 序列的位数 */
    private final long sequenceBits = 7L;

    /** 生成序列的最大值  */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 时间截向左移7位 */
    private final long timestampLeftShift = sequenceBits;


    /** 毫秒内序列() */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;


    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = getCurrentTime();

        //如果当前时间小于上一次生成的时间戳，说明系统时钟回退过就抛出异常
        if (timestamp < lastTimestamp) {
            throw new IllegalArgumentException("当前时间小于上一次生成的时间戳");
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {  //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成56位的ID
        return ((timestamp - epoch) << timestampLeftShift) // 计算时间戳
                | sequence; // 序列号
    }

    /**
     *获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTime();
        // 若是当前时间等于上一次的时间就一直阻塞
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTime();
        }
        return timestamp;
    }

    /**
     * 获取当前时间
     * @return 当前时间(毫秒)
     */
    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

}

