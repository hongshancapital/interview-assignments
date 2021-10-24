package com.interview.assignment.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockedRedisUtil {

  Map<String, String> cache = new ConcurrentHashMap<>(255);

  public void set(String key, String value) {
    ThreadUtil.sleepRandom(1000);
    cache.put(key, value);
  }

  public String get(String key) {
    ThreadUtil.sleepRandom(1000);
    return cache.get(key);
  }

  public boolean setNX(String key, String value) {
    return cache.putIfAbsent(key, value) == null;
  }


  /**
   * 简单模拟redis锁
   *
   * @param key 需要锁的key
   */
  public void lock(String key) {
    while (true) {
      boolean locked = setNX(key, "1");
      if (locked) {
        return;
      }
    }
  }

  /**
   * 简单模拟redis解锁
   *
   * @param key 需要解锁的key
   */
  public void unlock(String key) {
    cache.remove(key);
  }
}
