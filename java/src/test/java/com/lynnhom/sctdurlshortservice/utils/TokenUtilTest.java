package com.lynnhom.sctdurlshortservice.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description: TokenUtil测试类
 * @author: Lynnhom
 * @create: 2021-11-10 00:13
 **/
public class TokenUtilTest {

    @Test
    void testIsValid() {
        Assertions.assertEquals(Boolean.FALSE, TokenUtil.isValid("004",
                "https://github.com/scdt-china/interview-assignments",
                "7f909f46357375ef2d81635e626800c6"));
        Assertions.assertEquals(Boolean.TRUE, TokenUtil.isValid("003",
                "https://github.com/scdt-china/interview-assignments",
                "7f909f46357375ef2d81635e626800c6"));
        Assertions.assertEquals(Boolean.FALSE, TokenUtil.isValid("002",
                "https://github.com/scdt-china/interview-assignments",
                "7f909f46357375ef2d81635e626800c6"));
    }
}
