package com.sequoia.util;

import com.google.common.collect.Lists;
import com.sequoia.infrastructure.util.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Descript:
 * File: com.sequoia.util.HexUtilTest
 * Author: daishengkai
 * Date: 2022/3/31
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
public class HexUtilTest {

    @Test
    public void testHex() {
        char checkCode = HexUtil.getCheckCode("123");
        Assertions.assertEquals('d', checkCode);

        for (Long originLng : Lists.newArrayList(0L, 1000001L, 9999999999L)) {
            String hexCode = HexUtil.hex10To62(originLng);
            long originLong = HexUtil.hex62To10(hexCode);
            Assertions.assertEquals(originLng, originLong);
        }

        Long originLng = -1L;
        String hexCode = HexUtil.hex10To62(originLng);
        long originLong = HexUtil.hex62To10(hexCode);
        Assertions.assertNotEquals(originLng, originLong);

        try {
            long origin = HexUtil.hex62To10("oksfs-1~");
        } catch (Exception e) {
            log.error("异常", e);
        }
    }
}
