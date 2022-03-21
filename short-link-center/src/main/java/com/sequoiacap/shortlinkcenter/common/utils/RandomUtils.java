package com.sequoiacap.shortlinkcenter.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
@Slf4j
public class RandomUtils {

    private static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    /**
     * 随机获取任意随机数
     * @return 随机数
     */
    public static int nextInt() {
        return threadLocalRandom.nextInt();
    }

    /**
     * 随机获取指定随机数
     * @param bound 随机数值
     * @return 随机数
     */
    public static int nextInt(int bound) {
        if (bound <= 0) {
            log.error("RandomUtils nextInt bound: {} <= 0", bound);
            return threadLocalRandom.nextInt();
        }
        return threadLocalRandom.nextInt(bound);
    }

    /**
     * 随机获取指定范围随机数
     * @param origin 开始随机数值
     * @param bound 结束随机数值
     * @return 随机数
     */
    public static int nextInt(int origin, int bound) {
        if (origin >= bound) {
            log.error("RandomUtils nextInt origin: {} >= bound: {}", origin, bound);
            return threadLocalRandom.nextInt();
        }
        return threadLocalRandom.nextInt(origin, bound);
    }

    /**
     * 随机获取任意随机数
     * @return 随机数
     */
    public static long nextLong() {
        return threadLocalRandom.nextLong();
    }

    /**
     * 随机获取指定随机数
     * @param bound 随机数值
     * @return 随机数
     */
    public static long nextLong(long bound) {
        if (bound <= 0) {
            log.error("RandomUtils nextLong bound: {} <= 0", bound);
            return threadLocalRandom.nextLong();
        }
        return threadLocalRandom.nextLong(bound);
    }

    /**
     * 随机获取指定范围随机数
     * @param origin 开始随机数值
     * @param bound 结束随机数值
     * @return 随机数
     */
    public static long nextLong(long origin, long bound) {
        if (origin >= bound) {
            log.error("RandomUtils nextLong origin: {} >= bound: {}", origin, bound);
            return threadLocalRandom.nextLong();
        }
        return threadLocalRandom.nextLong(origin, bound);
    }

    /**
     * 随机获取任意随机数
     * @return 随机数
     */
    public double nextDouble() {
        return threadLocalRandom.nextDouble();
    }

    /**
     * 随机获取指定随机数
     * @param bound 随机数值
     * @return 随机数
     */
    public double nextDouble(double bound) {
        if (!(bound > 0.0)) {
            log.error("RandomUtils nextDouble bound: {} !(bound > 0.0)", bound);
            return threadLocalRandom.nextDouble();
        }
        return threadLocalRandom.nextDouble(bound);
    }

    /**
     * 随机获取指定范围随机数
     * @param origin 开始随机数值
     * @param bound 结束随机数值
     * @return 随机数
     */
    public double nextDouble(double origin, double bound) {
        if (!(origin < bound)) {
            log.error("RandomUtils nextDouble origin: {} !(origin < bound) bound: {}", origin, bound);
            return threadLocalRandom.nextDouble();
        }
        return threadLocalRandom.nextDouble(origin, bound);
    }
}
