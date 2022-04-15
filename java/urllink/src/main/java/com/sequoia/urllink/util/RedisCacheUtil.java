package com.sequoia.urllink.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Component
public class RedisCacheUtil {
  private final static Logger LOGGER = LoggerFactory.getLogger(RedisCacheUtil.class.getClass());
  private final static String REDIS_MAX_POOL = "spring.redis.pool.max-active";
  private static Executor executor;
  private static RedisTemplate<Object, Object> redisTemplate;

  private RedisCacheUtil() {
  }

  public static RedisTemplate<Object, Object> getRedisTemplate() {
    return redisTemplate;
  }

  @Autowired
  public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
    RedisCacheUtil.redisTemplate = redisTemplate;
  }

  /**
   * 获取到redis的分布式锁
   * <p>
   * 支持的最大并发为：redis最大连接池数量
   *
   * @param lockName
   * @param timeOutSeconds
   */
  public static void lock(String lockName, long timeOutSeconds) {
    redisTemplate.execute(new RedisCallback<Boolean>() {

      @Override
      public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

        Boolean addLock = Boolean.FALSE;

        try {
          byte[] key = lockName.getBytes(StandardCharsets.UTF_8);
          byte[] maxWaitTime =
              String.valueOf(System.currentTimeMillis() + timeOutSeconds * 1000L).getBytes(StandardCharsets.UTF_8);

          for (; !addLock; ) {
            addLock = connection.setNX(key, maxWaitTime);
            if (Objects.nonNull(addLock) && addLock) {
              connection.expire(key, timeOutSeconds);
            } else {
              byte[] value = connection.get(key);
              maxWaitTime =
                  String.valueOf(System.currentTimeMillis() + timeOutSeconds * 1000L).getBytes(StandardCharsets.UTF_8);

              if (Objects.isNull(value)) {
                continue;
              }

              long val = Long.valueOf(new String(value, StandardCharsets.UTF_8));
              if (System.currentTimeMillis() > val) {
                byte[] value2 = connection.getSet(key, maxWaitTime);
                if (Objects.isNull(value2) || value.equals(value2)) {
                  addLock = Boolean.TRUE;
                  connection.expire(key, timeOutSeconds);
                  continue;
                }
              } else {
                TimeUnit.MILLISECONDS.sleep(timeOutSeconds);
              }
            }

          }

        } catch (InterruptedException ex) {
          LOGGER.error(ex.getLocalizedMessage(), ex);
        } finally {
          connection.close();
        }

        return Boolean.TRUE;
      }
    });

  }

  /**
   * 获取到redis的分布式锁，不受redis最大连接池影响
   *
   * @param lockName
   * @param timeOutSeconds
   */
  public static void lockSafe(String lockName, long timeOutSeconds) {
    if (Objects.isNull(executor)) {
      initExecutor();
    }

    CountDownLatch downLatch = new CountDownLatch(1);
    executor.execute(new Runnable() {
      @Override
      public void run() {
        lock(lockName, timeOutSeconds);
        downLatch.countDown();
      }
    });

    try {
      downLatch.await();
    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   * 尝试获取redis的分布式锁
   * <p>
   * 支持的并发有限
   *
   * @param lockName
   * @return
   */
  public static boolean tryLock(String lockName, long timeOutSeconds) {
    return redisTemplate.execute(new RedisCallback<Boolean>() {

      @Override
      public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

        Boolean addLock = Boolean.FALSE;

        try {
          byte[] key = lockName.getBytes(StandardCharsets.UTF_8);
          byte[] maxWaitTime =
              String.valueOf(System.currentTimeMillis() + timeOutSeconds * 1000L).getBytes(StandardCharsets.UTF_8);

          addLock = connection.setNX(key, maxWaitTime);
          if (Objects.nonNull(addLock) && addLock) {
            connection.expire(key, timeOutSeconds);
          } else {
            byte[] value = connection.get(key);
            maxWaitTime =
                String.valueOf(System.currentTimeMillis() + timeOutSeconds * 1000L).getBytes(StandardCharsets.UTF_8);

            if (Objects.nonNull(value)) {
              long val = Long.valueOf(new String(value, StandardCharsets.UTF_8));
              if (System.currentTimeMillis() > val) {
                byte[] value2 = connection.getSet(key, maxWaitTime);
                if (Objects.isNull(value2) || value.equals(value2)) {
                  addLock = Boolean.TRUE;
                  connection.expire(key, timeOutSeconds);
                }
              }
            } else {
              addLock = tryLock(lockName, timeOutSeconds);
            }
          }
        } finally {
          connection.close();
        }

        return addLock;
      }
    });
  }

  /**
   * 尝试获取redis的分布式锁，不受redis最大连接池影响
   *
   * @param lockName
   * @param timeOutSeconds
   */
  public static boolean tryLockSafe(String lockName, long timeOutSeconds) {
    if (Objects.isNull(executor)) {
      initExecutor();
    }

    CountDownLatch downLatch = new CountDownLatch(1);
    AtomicBoolean addLock = new AtomicBoolean(false);
    executor.execute(new Runnable() {
      @Override
      public void run() {
        addLock.set(tryLock(lockName, timeOutSeconds));
        downLatch.countDown();
      }
    });

    try {
      downLatch.await();
    } catch (Exception ex) {
      LOGGER.error(ex.getLocalizedMessage(), ex);
    }

    return addLock.get();
  }

  /**
   * 释放锁
   *
   * @param lockName
   * @return
   */
  public static void unlock(String lockName) {
    redisTemplate.delete(lockName);
  }

  /**
   * 初始化线程池
   */
  private synchronized static void initExecutor() {
    if (Objects.isNull(executor)) {
      String maxNum = "15";
      //预留一个连接池，用来释放锁
      int total = StringUtils.isBlank(maxNum) ? 1 : Integer.parseInt(maxNum) - 1;
      executor = Executors.newFixedThreadPool(total);
    }
  }
}
