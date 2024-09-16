package com.lisi.urlconverter.controller;

import com.lisi.urlconverter.starter.Starter;
import com.lisi.urlconverter.vo.UCResponse;
import constant.CommonConstant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: ConverterController测试类
 * @author: li si
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class ConverterControllerTest {
    @Autowired
    private ConverterController converterController;

    @Test
    public void testConvertAndGet() {
        String longUrl = "veryLongUrl";
        UCResponse convertResponse = converterController.convert(longUrl);
        Assert.assertEquals(convertResponse.getRespCode(), CommonConstant.SUCCESS_RESPONSE_CODE);
        String shortUrl = (String) convertResponse.getData();

        UCResponse longResponse = converterController.get(shortUrl);
        String responseLongUrl = (String) longResponse.getData();
        Assert.assertEquals(longUrl, responseLongUrl);
    }

    @Test
    public void testConvertUrlExceedLimit() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
            builder.append("1");
        }
        UCResponse response = converterController.convert(builder.toString());
        Assert.assertEquals(response.getRespCode(), CommonConstant.VALIDATION_FAILED_RESPONSE_CODE);

        response = converterController.convert("");
        Assert.assertEquals(response.getRespCode(), CommonConstant.VALIDATION_FAILED_RESPONSE_CODE);
    }

    @Test
    public void testShortUrlInvalid() {
        UCResponse response = converterController.get(null);
        Assert.assertEquals(response.getRespCode(), CommonConstant.VALIDATION_FAILED_RESPONSE_CODE);

        response = converterController.get("longlonglongstr");
        Assert.assertEquals(response.getRespCode(), CommonConstant.VALIDATION_FAILED_RESPONSE_CODE);
    }
}
