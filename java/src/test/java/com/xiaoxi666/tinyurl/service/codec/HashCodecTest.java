package com.xiaoxi666.tinyurl.service.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/21
 * @Version: 1.0
 * @Description: hashids编解码
 */
class HashCodecTest {

    @Test
    void encode() {
        String result = HashCodec.encode(100);
        Assertions.assertEquals("y57", result); // 我们的salt固定时，编码结果是一样的。不要泄露salt即可。
    }

    @Test
    void decode() {
        long result = HashCodec.decode("y57");
        Assertions.assertEquals(100, result);
    }
}