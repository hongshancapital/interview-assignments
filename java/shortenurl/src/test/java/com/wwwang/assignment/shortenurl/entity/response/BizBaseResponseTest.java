package com.wwwang.assignment.shortenurl.entity.response;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BizBaseResponseTest {

    @Test
    public void test(){
        BizBaseResponse response = BizBaseResponse.success("test");
        Assert.assertEquals(response.getCode(),BizEnum.SUCCESS.getCode());
        Assert.assertEquals(response.getData(),"test");
        response = BizBaseResponse.operationFailed();
        Assert.assertEquals(response.getCode(),BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(),"系统错误，操作失败");
        response = BizBaseResponse.operationFailed("测试错误");
        Assert.assertEquals(response.getCode(),BizEnum.OPERATION_FAILED.getCode());
        Assert.assertEquals(response.getMessage(),"测试错误");
    }
}
