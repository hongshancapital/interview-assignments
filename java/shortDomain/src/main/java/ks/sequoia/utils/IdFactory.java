package ks.sequoia.utils;

import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

@Component
public class IdFactory {

    /**
     * 机器id所占的位数
     */
    private static final int WORK_LEN = 5;

    /**
     * 数据标识id所占的位数
     */
    private static final int DATA_LEN = 5;

    /**
     * 毫秒内序列的长度
     */
    private static final int SEQ_LEN = 12;

    /**
     * 时间部分所占长度
     */
    private static final int TIME_LEN = 41;

    /**
     * 开始时间截 (2015-01-01)
     */
    private static final long START_TIME = 1420041600000L;


    /**
     * 上次生成iD的时间戳
     */
    private static volatile long LAST_TIME_STAMP = -1L;


    /**
     * 时间部分向左移动的位数22(雪花算法总长度64,最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是 1)
     */
    private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;


    private static final long DATA_ID = getDataId();

    private static final long WORK_ID = getWorkId();

    /**
     * 数据中心id最大值 31
     */
    private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);

    /**
     * 数据中心id最大值 31
     */
    private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);


    /**
     * 机器随机获取数据中中心id的参数 32
     */
    private static final int DATA_RANDOM = DATA_MAX_NUM + 1;


    /**
     * 随机获取的机器id的参数
     */
    private static final int WORK_RANDOM = WORK_MAX_NUM + 1;

    /**
     * 数据中心id左移位数 17
     */
    private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;

    /**
     * 机器id左移位数 12
     */
    private static final int WROK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;

    /**
     * 上一次毫秒的序列值
     */
    private static volatile long LAST_SEQ = 0L;


    /**
     * 毫秒内序列的最大值 4095
     */
    private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);





    // ==============================Methods==========================================

    /**
     * 根据host name 取余，发生异常就获取0到31之间的随机数
     * @return
     */
    public static int getWorkId() {

        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(WORK_RANDOM);
        }

    }

    /**
     * 根据host name 取余，发生异常就获取0到31之间的随机数
     * @return
     */
    public static int getDataId() {

        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), DATA_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(DATA_RANDOM);
        }

    }

    /**
     * 根据host name 取余
     *
     * @return
     */
    private static int getHostId(String s, int max) {
        byte[] bytes = s.getBytes();
        int sums = 0;
        for (byte b : bytes) {
            sums += b;
        }
        return sums % (max + 1);
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public static  synchronized long nextId() {
        long now = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (now < LAST_TIME_STAMP) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", LAST_SEQ - now));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
            //毫秒内序列溢出
            if (LAST_SEQ == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                now = tilNextMillis(LAST_TIME_STAMP);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            LAST_SEQ = 0L;
        }

        //上次生成ID的时间截
        LAST_TIME_STAMP = now;

        //移位并通过或运算拼到一起组成64位的ID
        return ((now - START_TIME) << TIME_LEFT_BIT)
                | (DATA_ID << DATA_LEFT_BIT)
                | (WORK_ID << WROK_LEFT_BIT)
                | LAST_SEQ;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    public static  long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected static long timeGen() {
        return System.currentTimeMillis();
    }



}
