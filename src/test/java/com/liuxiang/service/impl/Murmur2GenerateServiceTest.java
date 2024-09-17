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
public class Murmur2GenerateServiceTest extends BaseTest {

    @Autowired
    private Murmur2GenerateService murmur2GenerateService;

    @Test
    public void generateType() {
        Assert.isTrue(GenerateTypeEnum.MUR_MUR.name().equals(murmur2GenerateService.generateType()));
    }

    @Test
    public void generate() {
        String shortId = murmur2GenerateService.generate("www.baidu.com");
        log.info("generate murmur:{}", shortId);
        Assert.isTrue("a507bcf9".equals(shortId));
    }
}