package com.liuxiang.service.impl;

import cn.hutool.core.lang.Assert;
import com.BaseTest;
import com.liuxiang.constant.GenerateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuxiang6
 * @date 2022-01-09
 **/
@Slf4j
public class CrcGenerateServiceTest extends BaseTest {

    @Autowired
    private CrcGenerateService crcGenerateService;

    @Test
    public void generateType() {
        Assert.isTrue(GenerateTypeEnum.CRC32.name().equals(crcGenerateService.generateType()));
    }

    @Test
    public void generate() {
        String shortId = crcGenerateService.generate("www.baidu.com");
        log.info("generate crc:{}", shortId);
        Assert.isTrue("0dc51b17".equals(shortId));
    }
}