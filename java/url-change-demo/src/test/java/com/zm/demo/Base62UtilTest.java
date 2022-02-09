package com.zm.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zm.demo.util.Base62Util;

/**
 * @ClassName Base62UtilTest
 * @Author zhaomin
 * @Date 2021/10/12 9:55
 **/
@SpringBootTest
public class Base62UtilTest {

    @Test
    void base62Encode(){
        String s = Base62Util.base62Encode(128L);
        Assertions.assertEquals("24", s);
    }

    @Test
    void base62Decode(){
        long s = Base62Util.base62Decode("24");
        Assertions.assertEquals(128L, s);
    }
}
