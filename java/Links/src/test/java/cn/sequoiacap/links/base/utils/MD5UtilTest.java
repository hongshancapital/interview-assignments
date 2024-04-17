package cn.sequoiacap.links.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : Liushide
 * @date :2022/4/6 11:05
 * @description : MD5功能类测试
 */
@Slf4j
class MD5UtilTest {

    @Test
    @DisplayName("测试 MD5Util 类的 md5Code 方法")
    void md5Code() {
        String s = "abc";
        String md5Str = MD5Util.md5Code(s);
        log.info("MD5后：{}", md5Str);
        assertEquals("900150983cd24fb0d6963f7d28e17f72", md5Str);
    }
}