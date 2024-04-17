package com.sequoia.infrastructure.service.impl;

import com.sequoia.infrastructure.util.Constant;
import com.sequoia.infrastructure.util.HexUtil;
import com.sequoia.service.IIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * IdWorkerTest
 *
 * @author KVLT
 * @date 2022-04-09.
 */
@Slf4j
@SpringBootTest
public class IdWorkerTest {

    @Resource(name = "murmurIdWorker")
    private IIdWorker murmurIdWorker;

    @Test
    public void testGenerateTinyCode() {
        String originUrl = "test.sequence.com";

        log.error("murmurHash算法生成");
        for (int i = 0; i < 10; i++) {
            log.error("生成的hashLong: {}", generateTinyCode(murmurIdWorker, i + originUrl));
        }
    }

    @Test
    public void testByteOperation() {
        log.error("{}, {}",  Constant.LONG_BITS_ALL_1, HexUtil.hex10To62(281474976710655L));
    }

    private String generateTinyCode(IIdWorker idWorker, String originUrl) {
        // 截取低位bit - 7位短码对应 42bit，8位短码对应 48bit
        long number = Constant.LONG_BITS_ALL_1 & idWorker.nextId(originUrl);
        // 将截取后bit的首位置1
        number = Constant.LONG_BITS_HEAD_1 | number;
        // 如果大于当前位62进制最大值，则bit集体右移1位
        number = (number > Constant.HEX62_MAX_LONG) ? number >> 1 : number;
        // 转换为62进制
        String hex62 = HexUtil.hex10To62(number);
        return HexUtil.getRandomHex62Char() + hex62;
//        return hex62;
    }
}
