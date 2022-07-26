package com.scdt.url.common;

import com.scdt.url.common.util.Base64Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Base64UtilsTest {

    @Test
    void testEncode_Decode() {
        long num = 1234567;
        var encode = Base64Utils.encode(num, 8);
        var decode = Base64Utils.decode(encode);
        assertEquals(num, decode);

    }
}
