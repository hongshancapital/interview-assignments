package com.scdt.sdn;

import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.scdt.sdn.util.EncodeUtil;
import com.scdt.sdn.util.SnowUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdnApplication.class)
public class EncodeUtilTest {

    @Autowired
    private EncodeUtil encodeUtil;

    @Autowired
    private SnowUtils snowUtils;

    @Test
    public void encodeAndDecodeTest() {
        Random random = new Random();
        int randomInt = random.nextInt(1000000);
        String num62 = encodeUtil.encode(randomInt);
        Assert.assertTrue("长度超过8位", num62.length() <= 8);
        Long numDecode = encodeUtil.decode(num62);
        Assert.assertEquals("加解密错误", randomInt, numDecode.intValue());
    }

    @Test
    public void generateIdAndEncodeAndDecodeTest() {
        for (int i = 0; i <= 100; i++) {
            Long num10 = snowUtils.nextId();
            String num62 = encodeUtil.encode(num10);
            Long numDecode = encodeUtil.decode(num62);
            Assert.assertEquals("加解密错误", num10, numDecode);
        }
    }

    @Test
    public void encodeLimitTest() {
        System.out.println(new Date());
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i <= 20000; i++) {
            try {
                Long num10 = snowUtils.nextId();
                String num62 = encodeUtil.encode(num10);
                Long numDecode = encodeUtil.decode(num62);
                Assert.assertEquals("加解密错误", num10, numDecode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Long costTime = System.currentTimeMillis() - startTime;
        System.out.println(new Date());
        System.out.println(costTime);
    }

}
