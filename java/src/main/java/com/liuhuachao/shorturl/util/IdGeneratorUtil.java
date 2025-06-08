package com.liuhuachao.shorturl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Id 生成器
 * 使用多个计数器（AtomicLong 原子操作）和 多线程安全随机数类（ThreadLocalRandom 产生步进数）生成自增 Id
 * @author liuhuachao
 * @date 2021/12/20
 */
public class IdGeneratorUtil {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(IdGeneratorUtil.class);

	/**
	 * 计数器 1
	 */
	private static final AtomicLong COUNT1 = new AtomicLong(10000);

	/**
	 * 计数器 2
	 */
	private static final AtomicLong COUNT2 = new AtomicLong(10001);

	/**
	 * 计数器 3
	 */
	private static final AtomicLong COUNT3 = new AtomicLong(10002);
	
	/**
	 * 步进数等于计数器的数量
	 */
	private static final int STEP = 3;

	/**
	 * 获取自增 Id
	 * @return
	 */
	public static long nextId() {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		int randNum = threadLocalRandom.nextInt(STEP);
		long id = 0;
		switch (randNum){
			case 1:
				id = COUNT2.getAndAdd(STEP);
				break;
			case 2:
				id = COUNT3.getAndAdd(STEP);
				break;
			default:
				id = COUNT1.getAndAdd(STEP);
				break;
		}
		return id;
	}

}
