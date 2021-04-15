package com.alice.shortdomain.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/15 18:05
 */
class RandomStringUtilTest {

    private final Logger log = LoggerFactory.getLogger(RandomStringUtilTest.class);

    @Test
    void randomStr() {

        log.info(RandomStringUtil.randomStr(6));

    }
}