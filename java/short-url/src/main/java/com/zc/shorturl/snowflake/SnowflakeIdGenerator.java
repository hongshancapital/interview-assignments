package com.zc.shorturl.snowflake;

import com.google.common.base.Preconditions;
import com.zc.shorturl.snowflake.common.Result;
import com.zc.shorturl.snowflake.config.SnowflakeIdGeneratorProperties;
import com.zc.shorturl.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.annotations.VisibleForTesting;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;


/**
 * 缩短版的snowflake
 * 8位字符62进制可表示最大数: 218340105584896 - 1
 * 47位2进制可表示的总数：    140737488355328 - 1
 * 所以64位(实际有用63位)的snowflake可缩减到47位， 总共需要缩减16位：
 * 机器ID从10位改成3位，自增序列号从12位改成3位
 * @description  SnowflakeIdGenerator
 */
@Slf4j
@Component
@EnableConfigurationProperties(SnowflakeIdGeneratorProperties.class)
public class SnowflakeIdGenerator implements IdGenerator {
    // 开始时间截 (从2022-01-01起)
    private static final long START_TIME = 1640966400000L;

    // 机器ID (3bit， 支持最大机器数 2^3 = 8)
    private static final long WORKER_ID_BITS = 3L;

    // 机器ID最大值 (2^3 - 1 = 7)
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    // 自增序列号 (3bit， 支持单节点毫秒内最多生成 2^3 = 8)
    private static final long SEQUENCE_BIT = 3L;

    // 单节点毫秒内自增序列号最大值 (2^3 - 1 = 7)
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 机器ID的偏移量：工作机器ID位于位序列号左边，所以序列号就是它的偏移量
    private static final long WORKER_ID_SHIFT_BITS = SEQUENCE_BIT;

    // 时间戳偏移量： 时间戳位于工作机器ID的左边，所以 (序列号 + 工作机器ID) = 时间戳偏移量
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = SEQUENCE_BIT + WORKER_ID_BITS;

    // 时钟回拨可允许到阈值，单位ms
    private static final long MAX_MOVED_BACK_OFFSET = 3;

    // 当前工作机器的ID
    private long workerId;

    // 当前序列号
    private long sequence = 0L;

    // 最后更新时间
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     * @param snowflakeIdGeneratorProperties  配置属性
     */
    public SnowflakeIdGenerator(SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties) {
        Preconditions.checkArgument(getCurrentTmp() >= START_TIME, "start time must lte to current time !");
        //获取当前服务器的某个网卡的IP
        final String ip = IpUtils.getIp();
        SnowflakeZookeeperHolder holder = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties, ip);
        boolean initFlag = holder.init();
        if (initFlag) {
            this.workerId = holder.getWorkerId();
            if (workerId >= 0 && workerId <= MAX_WORKER_ID) {
                log.info("Zookeeper initialized successfully , current work id is : {}", workerId);
            }
            else {
                throw new IllegalStateException(
                        String.format("Zookeeper initialized successfully , but work id %d is not available , because work id must gte %d and lte %d"
                                ,workerId,0, MAX_WORKER_ID));
            }
        }
        else {
            throw new IllegalStateException("Zookeeper initialized failed!");
        }
        log.info("SnowflakeIdGenerator was created!");
    }

    public synchronized Result nextId() {
        long curTmp = getCurrentTmp();
        log.info("curTmp:{}, lastTimestamp:{}", curTmp, lastTimestamp);
        //时钟回拨
        if (curTmp < lastTimestamp) {
            long offset = lastTimestamp - curTmp;
            //如果回拨在可接受的范围之内，就等其一倍的时间
            if (offset <= MAX_MOVED_BACK_OFFSET) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(MAX_MOVED_BACK_OFFSET << 1));
                curTmp = getCurrentTmp();
                if (curTmp < lastTimestamp) {
                    return Result.systemClockGoBack();
                }
            }
            else {
                //回拨步长太大，直接返回错误结果
                return Result.systemClockGoBack();
            }
        }
        //相同时间内，就自增序列
        else if (lastTimestamp == curTmp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //当前毫秒内的序列已达到最大
            if (sequence == 0) {
                curTmp = tillNextMillis(lastTimestamp);
            }
        }
        else {
            //正常获取，重置序列
            sequence = 0L;
        }
        lastTimestamp = curTmp;
        long id = ((curTmp - START_TIME) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_SHIFT_BITS) | sequence;
        return Result.ok(id);
    }

    //阻塞直到下一毫秒
    private long tillNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTmp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTmp();
        }
        return timestamp;
    }

    // 获取当前时间
    private long getCurrentTmp() {
        return System.currentTimeMillis();
    }

//    // 获取当前工作机器ID
//    public long getWorkerId() {
//        return workerId;
//    }

    @VisibleForTesting
    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    @VisibleForTesting
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    @VisibleForTesting
    public long getMaxSequence() {
        return MAX_SEQUENCE;
    }

    @VisibleForTesting
    public static long getMaxWorkerId() {
        return MAX_WORKER_ID;
    }
}
