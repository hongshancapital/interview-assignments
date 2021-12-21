package com.hongshang.shorturlmodel.impl;

import mockit.Tested;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 对雪花策略类进行测试
 *
 * @author kobe
 * @date 2021/12/19
 */
public class SnowflakeGetShortStrStrategyTest {

    @Tested
    SnowflakeGetShortStrStrategy snowflakeGetShortStrStrategy;

    @Test
    public void getNextShortStr() {
        snowflakeGetShortStrStrategy.getNextShortStr();
    }
}