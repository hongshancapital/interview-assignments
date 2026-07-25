package com.liuxiang.service.impl;

import cn.hutool.core.lang.Assert;
import com.BaseTest;
import com.liuxiang.constant.GenerateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuxiang6
 * @date 2022-01-09
 **/
@Slf4j
public class RandomGenerateServiceTest extends BaseTest {

    @Autowired
    private RandomGenerateService randomGenerateService;

    @Test
    public void generateType() {
        Assert.isTrue(GenerateTypeEnum.RANDOM.name().equals(randomGenerateService.generateType()));
    }

    @Test
    public void generate() {
        String shortId = randomGenerateService.generate("www.baidu.com");
        log.info("generate random:{}", shortId);
        Assert.isTrue(StringUtils.isNotBlank(shortId));
    }
}