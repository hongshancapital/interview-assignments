package cn.sequoiacap.interview.xurl.util;

import cn.sequoiacap.interview.xurl.config.AppConfig;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

/** ID生成器工具，实现了自定义的雪花算法 */
@Slf4j
public class IDGenerator {
  // 序列号4bit最大值15
  private static final int NO_THRESHOLD = 15;
  // 机器码3bit最大值7
  private static final int MACHINE_THRESHOLD = 7;
  // 时间戳基准值，因为设定40bit最多存储三十多年，所以从2022-01-01 00：00：00起
  // static 块内初始化
  private static final long BASE_TIMESTAMP;

  private static final ReentrantLock NO_LOCK = new ReentrantLock();

  private static int globalNo = 0;
  private static long milSec = 0;
  private static volatile int machineNo = -1;

  // 初始化 BASE_TIMESTAMP
  static {
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(fmt.parse("2022-01-01 00:00:00"));
    } catch (ParseException ignored) {
    }
    BASE_TIMESTAMP = calendar.getTimeInMillis();
  }

  public static class OutOfIndexError extends Exception {
    public OutOfIndexError(String message) {
      super(message);
    }
  }

  private static void checkMachineNo() throws OutOfIndexError {
    if (machineNo == -1) {
      // 从配置初始化机器码
      AppConfig config = SpringUtil.getBean(AppConfig.class);
      machineNo = config.getMachineNo();
      if (machineNo > MACHINE_THRESHOLD) {
        throw new OutOfIndexError("generate id error: machine out of range");
      }
    }
  }

  /** 清理测试数据 */
  public static void clear() {
    machineNo = -1;
  }

  /**
   * 获取一个新的ID
   *
   * @return <long> 新ID，小于2的48次方
   * @throws OutOfIndexError 某些情况下超出生成限制或性能瓶颈
   */
  public static long generate() throws OutOfIndexError {
    long timestampDelta; // 时间戳
    int num; // 序列号

    // 时间戳和序列号需要加锁同步获取
    NO_LOCK.lock();
    try {
      timestampDelta = System.currentTimeMillis() - BASE_TIMESTAMP;
      if (milSec == timestampDelta) {
        // 同一毫秒，序列号加一
        num = ++globalNo;
        if (num > NO_THRESHOLD) {
          // 序列号超出设计上限
          throw new OutOfIndexError("generate id error: no out of range");
        }
      } else {
        // 新的毫秒，序列号归零
        num = 0;
        globalNo = 0;
        milSec = timestampDelta;
      }
    } finally {
      NO_LOCK.unlock();
    }

    // 检查机器码
    checkMachineNo();

    // 组合序列号
    long id = 0L;
    id |= timestampDelta << 7;
    id |= (long) machineNo << 4;
    id |= num;

    return id;
  }
}
