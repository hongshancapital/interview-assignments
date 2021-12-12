package com.breo.common.helper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeGidHelper {

	/** 数据中心 */
	@Value("${common.datacenterId}")
	private long datacenterId;

	/** 机器标识 */
	@Value("${common.machineId}")
	private long machineId;

	/** 起始的时间戳 */
	private final static long START_STMP = 1639232030249L;
	/** 序列号占用的位数 */
	private final static long SEQUENCE_BIT = 10;
	/** 机器标识占用的位数 */
	private final static long MACHINE_BIT = 4;
	/** 数据中心占用的位数 */
	private final static long DATACENTER_BIT = 4;
	/** 数据中心最大值 */
	private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
	/** 机器标识最大值 */
	private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
	/** 序列号最大值 */
	private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
	/** 机器标识部分 向左的位移 */
	private final static long MACHINE_LEFT = SEQUENCE_BIT;
	/** 数据中心部分 向左的位移 */
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
	/** 时间戳部分 向左的位移 */
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

	/** 序列号 */
	private long sequence = 0L;
	/** 上一次时间戳 */
	private long lastStmp = -1L;

	
    @PostConstruct
    public void init() {
		if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
			throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
		}
		if (machineId > MAX_MACHINE_NUM || machineId < 0) {
			throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
		}
    }

	/**
	 * 产生下一个ID
	 */
	public synchronized long nextId() {
		long currStmp = getNewstmp();
		if (currStmp < lastStmp) {
			throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
		}

		if (currStmp == lastStmp) {
			// 相同毫秒内，序列号自增
			sequence = (sequence + 1) & MAX_SEQUENCE;
			// 同一毫秒的序列数已经达到最大4096
			if (sequence == 0L) {
				currStmp = getNextMill();
			}
		} else {
			// 不同毫秒内，序列号置为0
			sequence = 0L;
		}

		lastStmp = currStmp;

		return (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分
				| datacenterId << DATACENTER_LEFT // 数据中心部分
				| machineId << MACHINE_LEFT // 机器标识部分
				| sequence; // 序列号部分
	}

	/**
	 * 自循环获取下一毫秒时间
	 * 
	 * @return
	 */
	private long getNextMill() {
		long mill = getNewstmp();
		while (mill <= lastStmp) {
			mill = getNewstmp();
		}
		return mill;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	private long getNewstmp() {
		return System.currentTimeMillis();
	}

}
