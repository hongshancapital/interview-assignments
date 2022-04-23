package com.shortlink.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MurmurHashUtilTest {
    @Test
    public void testHash(){
        String hash = new String(MurmurHashUtil.hash("http://www.baidu.com"));
        log.info("hash: " + hash);
    }
}
