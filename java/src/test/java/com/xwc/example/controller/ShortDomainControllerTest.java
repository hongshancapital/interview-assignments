package com.xwc.example.controller;

import com.xwc.example.ShortDomainApplication;
import com.xwc.example.commons.model.Result;
import com.xwc.example.controller.ShortDomainController;
import com.xwc.example.controller.dto.DomainDto;
import com.xwc.example.service.impl.MemoryDomainServiceImpl;
import com.xwc.example.utils.TestDataUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

/**
 * 类描述：单元测试短域名接口
 * 作者：徐卫超 (cc)
 * 时间 2022/4/13 19:22
 */
@SuppressWarnings("all")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortDomainApplication.class)
public class ShortDomainControllerTest {

    @Value("${domain.maxCapacity:10000000}")
    private Long maxCapacity;

    @Autowired
    private ShortDomainController shortDomainController;
    @Autowired
    private MemoryDomainServiceImpl memoryDomainService;

    // 正常逻辑测试
    @Test
    public void longDomainToShortTest() {
        Result<DomainDto> result = shortDomainController.LongDomainToShort(DomainDto.convert(TestDataUtils.genderLongDomain()));
        Assert.assertEquals(Result.SUCCESS, result.getCode());
    }

    @Test
    public void longDomainToShortMaxCapacityTest() {
        for (int i = 0; i < maxCapacity + 10; i++) {
            Result<DomainDto> result = shortDomainController.LongDomainToShort(DomainDto.convert(TestDataUtils.genderLongDomain()));
            if (Result.FAILED == result.getCode()) {
                memoryDomainService.clear();
                Assert.assertEquals("短域名服务超过上限", result.getMessage());
                return;
            }
        }
        Assert.fail();
    }

    // 异常情况测试
    @Test
    public void longDomainToShortFailedTest() {
        // 空数据测试
        Result<DomainDto> result =
                shortDomainController.LongDomainToShort(DomainDto.convert(null));
        Assert.assertEquals(Result.FAILED, result.getCode());
    }

    // 正常逻辑测试
    @Test
    public void shortDomainToLongTest() {
        Result<DomainDto> result =
                shortDomainController.LongDomainToShort(DomainDto.convert(TestDataUtils.genderLongDomain()));
        result = shortDomainController.shortDomainToLong(result.getData().getAddress());
        Assert.assertEquals(Result.SUCCESS, result.getCode());
    }

    // 异常情况测试
    @Test
    public void shortDomainToLongFailedTest() {
        // 空数据测试
        Result<DomainDto> result = shortDomainController.shortDomainToLong(null);
        Assert.assertEquals(Result.FAILED, result.getCode());
        // 不存在的数据测试
        result = shortDomainController.shortDomainToLong("12312312312");
        Assert.assertEquals(Result.FAILED, result.getCode());

    }

}
