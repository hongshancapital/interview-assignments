package com.sequoia.domain.service.impl;

import com.sequoia.domain.service.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 雪花分布式ID算法
 */
@Service
@ConditionalOnProperty(prefix = "app.id.generator", name = "algorithm", havingValue = "snow_flake")
public class SnowFlakeIdGenerator implements IdGenerator {
    private final static long START_TIMESTAMP = 1609430400000L; // 起始时间戳 2021.01.01 00:00:00

    private final static long SEQUENCE_BIT = 12;   //序列号占用的位数
    private final static long MACHINE_BIT = 5;     //机器标识占用的位数
    private final static long DATA_CENTER_BIT = 5; //数据中心占用的位数

    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private final long dataCenterId;
    private final long machineId;
    private long sequence = 0L;
    private long lastedTimeStamp = -1L;

    /**
     * 根据指定的数据中心ID和机器标志ID生成指定的序列号
     *
     * @param dataCenterId 数据中心ID
     * @param machineId    机器标志ID
     */
    @Autowired
    public SnowFlakeIdGenerator(@Value("${app.id.generator.data_center_id}") long dataCenterId, @Value("${app.id.generator.machine_Id}") long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("DtaCenterId can't be greater than " + MAX_DATA_CENTER_NUM + " or less than 0！");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("MachineId can't be greater than " + MAX_MACHINE_NUM + " or less than 0！");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 生成下一个ID
     *
     * @return id
     */
    @Override
    public synchronized long nextId() {
        long currTimeStamp = System.currentTimeMillis();
        if (currTimeStamp < lastedTimeStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currTimeStamp == lastedTimeStamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                currTimeStamp = getNextMill();
            }
        } else {
            sequence = 0L;
        }

        lastedTimeStamp = currTimeStamp;
        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT | dataCenterId << DATA_CENTER_LEFT | machineId << MACHINE_LEFT | sequence;
    }

    /**
     * 用于前一个毫秒序列号用满的情况，阻塞到下一毫秒
     *
     * @return 等待后的时间戳
     */
    private long getNextMill() {
        long mill = System.currentTimeMillis();
        while (mill <= lastedTimeStamp) {
            mill = System.currentTimeMillis();
        }
        return mill;
    }
}