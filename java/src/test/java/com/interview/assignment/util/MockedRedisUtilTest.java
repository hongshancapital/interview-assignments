package com.interview.assignment.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MockedRedisUtilTest {
  @Test
  public void testSet() {
    MockedRedisUtil redisUtil = new MockedRedisUtil();
    redisUtil.set("test", "test");
    assertEquals(1, redisUtil.cache.size());
  }

  @Test
  public void testGet() {
    MockedRedisUtil redisUtil = new MockedRedisUtil();
    redisUtil.set("test", "test");
    String result = redisUtil.get("test");
    assertEquals("test", result);
  }

  @Test
  public void testSetNX() {
    MockedRedisUtil redisUtil = new MockedRedisUtil();
    boolean result = redisUtil.setNX("test", "test");
    assertTrue(result);

    result = redisUtil.setNX("test", "test1");
    assertFalse(result);

    assertEquals("test", redisUtil.cache.get("test"));
  }

  @Test
  public void testLock() {
    MockedRedisUtil redisUtil = new MockedRedisUtil();
    redisUtil.lock("test");
  }

  @Test
  public void testUnlock() {
    MockedRedisUtil redisUtil = new MockedRedisUtil();
    redisUtil.lock("test");
    redisUtil.unlock("test");
    redisUtil.lock("test");
    redisUtil.unlock("test");
  }
}
