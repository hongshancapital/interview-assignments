package com.lisi.urlconverter.util;

import com.lisi.urlconverter.enumeration.UCErrorType;
import com.lisi.urlconverter.starter.Starter;
import com.lisi.urlconverter.vo.UCResponse;
import constant.CommonConstant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 测试Response工具类
 * @author: li si
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class ResponseUtilTest {
    @Test
    public void testBuildErrorResponse() {
        UCResponse response = ResponseUtil.buildErrorResponse(UCErrorType.COMMON_EXCEPTION);
        Assert.assertEquals(response.getRespCode(), CommonConstant.FAILED_RESPONSE_CODE);
    }
}
