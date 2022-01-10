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
public class Md5GenerateServiceTest extends BaseTest {

    @Autowired
    private Md5GenerateService md5GenerateService;

    @Test
    public void generateType() {
        Assert.isTrue(GenerateTypeEnum.MD5.name().equals(md5GenerateService.generateType()));
    }

    @Test
    public void generate() {
        String shortId = md5GenerateService.generate("www.baidu.com");
        log.info("generate md5:{}", shortId);
        Assert.isTrue("dab19e82".equals(shortId));
    }
}