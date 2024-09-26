package com.example.shortlink.util;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public class IdUtil {

  //代表一毫秒内生成的多少个id的最新序号
  private long sequence;

  //每毫秒内产生的序号所占用的位数
  private int sequenceBits;

  // 每毫秒中生成序号的最大值
  private long maxSequence;

  //记录产生时间毫秒数，判断是否是同1毫秒
  private long lastTimestamp = -1L;

  //初始化时间
  private long initTimestamp = 1640591183329L;

  /**
   * 构造函数.
   *
   * @param bit 每毫秒内产生的序号所占用的位数
   */
  public IdUtil(int bit) {
    sequenceBits = bit;
    sequence = 2L << bit - 1;
    maxSequence = sequence - 1;
  }

  // 这个是核心方法，通过调用nextId()方法，让当前这台机器上的snowflake算法程序生成一个全局唯一的id
  public synchronized long nextId() {
    // 这儿就是获取当前时间戳，单位是毫秒
    long timestamp = nowTimestamp();
    if (timestamp < lastTimestamp) {
      throw new RuntimeException(
          String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }

    // 同一毫秒内累加
    if (lastTimestamp == timestamp) {

      // 累加 且不能超过最大值
      sequence = (sequence + 1) & maxSequence;
      // 如果超过最大值 等待下一毫秒
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0;
    }
    // 这儿记录一下最近一次生成id的时间戳，单位是毫秒
    lastTimestamp = timestamp;
    // 将当前时间戳左移sequenceBits位，空出sequence的位数，加上sequence
    return ((timestamp - initTimestamp) << sequenceBits) | sequence;
  }

  /**
   * 下一毫秒.
   *
   * @param lastTimestamp 上一次的毫秒数.
   * @return 下一毫秒
   */
  private long tilNextMillis(long lastTimestamp) {

    long timestamp = nowTimestamp();

    while (timestamp <= lastTimestamp) {
      timestamp = nowTimestamp();
    }
    return timestamp;
  }

  /**
   * 获取当前时间戳.
   *
   * @return 当前时间戳
   */
  private long nowTimestamp() {
    return System.currentTimeMillis();
  }


}
