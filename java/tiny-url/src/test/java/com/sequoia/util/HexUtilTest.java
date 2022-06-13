package com.sequoia.util;

import com.google.common.collect.Lists;
import com.sequoia.infrastructure.util.Constant;
import com.sequoia.infrastructure.util.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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

    @Test
    public void testHex62to10() {
        // ZZZZZZZ - 3521614606207 - 333 f096 6f7f
        // zzzzzzz - 2020598544545 - 1d6 750e f8a1

        // ZZZZZZZZ - 218340105584895 - c694446f00ff
        // zzzzzzzz - 125277109761825 - 71f059a03721
        for (String hex62 : Lists.newArrayList("ZZZZZZZZ", "zzzzzzzz", "0000000")) {
            log.error("hex62: {} - {}", hex62, HexUtil.hex62To10(hex62));
        }

        log.error("{}", HexUtil.hex10To62(Long.MAX_VALUE));

        for (String tinyCode : Lists.newArrayList("SFS", "v12@", "---")) {
            log.error("{} 是否合格 {}", tinyCode, StringUtils.containsOnly(tinyCode, Constant.DIGITS_CHAR_ARR));
        }

    }

    @Test
    public void testBit() {
        int maxLength = 7;
        maxLength = 8;
        long maxLong = HexUtil.maxHex62(maxLength);
        int bitCount = Long.bitCount(maxLong);
        long highestLong = Long.highestOneBit(maxLong);

        log.error("位数： {}, {}, {}, {}", bitCount, maxLong, highestLong, maxLong | Constant.LONG_BITS_ALL_1);
    }

}
