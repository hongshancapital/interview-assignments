package com.liu.shorturl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description： 雪花算法ID生成器
 * Date: Created in 2021/11/11 17:48
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 *
 * @author hello
 */
public class SnowFlakeIdWorker implements IdWorker<java.lang.Long> {

    private Logger logger = LoggerFactory.getLogger(SnowFlakeIdWorker.class);

    /**
     * 工作id
     */
    private long workerId;
    /**
     * 数据id
     */
    private long datacenterId;
    /**
     * 12位的序列号
     */
    private long sequence;
    /**
     * 初始时间戳
     */
    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;

    private long datacenterIdBits = 5L;
    /**
     * 序列号id长度
     */
    private long sequenceBits = 12L;
    /**
     * 工作id需要左移的位数，12位
     */
    private long workerIdShift = sequenceBits;
    /**
     * 数据id需要左移位数 12+5=17位
     */
    private long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳需要左移位数 12+5+5=22位
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * 上次时间戳，初始值为负数
     */
    private long lastTimestamp = -1L;

    /**
     * 最大值
     */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);


    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);


    public SnowFlakeIdWorker(long workerId, long datacenterId, long sequence) {
        //workerId最大值校验
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        logger.info("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    /**
     * 生成唯一ID
     *
     * @return
     */
    @Override
    public java.lang.Long nextId() {
        long timestamp = timeGen();

        //将上次时间戳值刷新
        lastTimestamp = timestamp;

        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }


    /**
     * 获取当前系统时间戳
     *
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
