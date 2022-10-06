package com.xiaoxi666.tinyurl.service.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/21
 * @Version: 1.0
 * @Description: base62进制编码
 */
class Base62CodecTest {

    @Test
    @DisplayName("正常编码")
    void encode_normal() {
        String result = Base62Codec.encode(5);
        Assertions.assertEquals("E", result);
    }

    @Test
    @DisplayName("下限溢出")
    void encode_overflowLow() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Base62Codec.encode(0);
        }, "62进制编码失败，只支持1~62范围内的数字！传入的数字：0");
    }

    @Test
    @DisplayName("上限溢出")
    void encode_overflowHigh() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Base62Codec.encode(63);
        }, "62进制编码失败，只支持1~62范围内的数字！传入的数字：63");
    }
}