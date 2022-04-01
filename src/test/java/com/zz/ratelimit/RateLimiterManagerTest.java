package com.zz.ratelimit;

import com.zz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class RateLimiterManagerTest extends BaseTest {

    @Autowired
    private RateLimiterManager manager;

    @Test
    public void register() {
        manager.register(buildConfig());
    }

    private RateLimiterConfig buildConfig(){
        RateLimiterConfig config = new RateLimiterConfig();
        config.setKey("123");
        config.setQPS(10);
        config.setTimeout(10);
        config.setTimeUnit(TimeUnit.MICROSECONDS);
        return config;
    }

    @Test
    public void tryAcquire() {
        boolean res = manager.tryAcquire(buildConfig());
        Assert.assertTrue(res);
    }

    @Test
    public void isValid() {
        RateLimiterConfig config = buildConfig();
        boolean valid = manager.isValid(config);
        Assert.assertTrue(valid);

        config.setKey(null);
        valid = manager.isValid(config);
        Assert.assertFalse(valid);
    }

    @Test
    public void isValid_0() {
        RateLimiterConfig config = buildConfig();
        config.setKey(null);
        boolean valid = manager.isValid(config);
        Assert.assertFalse(valid);
    }

    @Test
    public void isValid_1() {
        RateLimiterConfig config = buildConfig();
        config.setQPS(RateLimiterAnno.NOT_LIMITED);
        boolean valid = manager.isValid(config);
        Assert.assertFalse(valid);
    }
}