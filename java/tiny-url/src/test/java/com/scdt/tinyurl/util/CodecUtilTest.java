package com.scdt.tinyurl.util;

import com.scdt.tinyurl.config.AppConfig;
import com.scdt.tinyurl.exception.GlobalException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CodecUtilTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    void encode62() {


        GlobalException exception = Assertions.assertThrows(GlobalException.class,() -> {
            CodecUtil.encode62(-1,appConfig.getCodecDict(),appConfig.getCodecLength());
        });

        Assert.assertEquals("401",exception.getCode());


        GlobalException exception2 = Assertions.assertThrows(GlobalException.class,() -> {
            CodecUtil.encode62((long)Math.pow(62,8) + 1,appConfig.getCodecDict(),appConfig.getCodecLength());
        });

        Assert.assertEquals("401",exception.getCode());

        Assert.assertEquals("401",exception2.getCode());

        Assert.assertEquals("UN6X80kU",CodecUtil.encode62(200000000000000L,appConfig.getCodecDict(),appConfig.getCodecLength()));

        Assert.assertEquals(8,CodecUtil.encode62(140000000934000L,appConfig.getCodecDict(),appConfig.getCodecLength()).length());

        System.out.println();

    }

    @Test
    void decode62() {

        Assert.assertEquals("200000000000000", CodecUtil.decode62("UN6X80kU",appConfig.getCodecDict(),appConfig.getMaxIdLength()));

        GlobalException exception = Assertions.assertThrows(GlobalException.class,() -> {
            CodecUtil.decode62(null,appConfig.getCodecDict(),appConfig.getMaxIdLength());
        });

        GlobalException exception2 = Assertions.assertThrows(GlobalException.class,() -> {
            CodecUtil.decode62("99999999999999999999999",appConfig.getCodecDict(),appConfig.getMaxIdLength());
        });


    }
}