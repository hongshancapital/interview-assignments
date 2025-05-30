package com.example.app.service.impl;

import com.example.app.AppApplicationTests;
import com.example.app.common.constants.Constants;
import com.example.app.common.exception.ModuleException;
import com.example.app.service.DomainService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

/**
 * @author voidm
 * @date 2021/9/19
 */
public class DomainServiceImplTest extends AppApplicationTests {

    @Autowired
    private DomainService domainService;

    String fullUrl = "http://voidm.com";

    /**
     * 测试生成短链
     */
    @Test
    public void generateShortUrl() throws ModuleException {

        log.info(String.format("测试生成短链请求 >> [%s]", fullUrl));
        String shortUrl = generateShortUrlWithFull(fullUrl);
        Assert.assertTrue(Pattern.matches(Constants.REGEX_URL,shortUrl));
        log.info(String.format("测试生成短链响应 << [%s]", shortUrl));

    }

    private String generateShortUrlWithFull(String url) throws ModuleException {
        return domainService.generateShortUrl(url);
    }

    /**
     * 测试获取长链
     */
    @Test
    public void parseWithShortUrl() throws ModuleException {

        String shortUrl = generateShortUrlWithFull(fullUrl);
        Assert.assertTrue(Pattern.matches(Constants.REGEX_URL,shortUrl));

        log.info(String.format("测试获取短链请求 >> [%s]", shortUrl));
        String fullUrlByParse = domainService.parseWithShortUrl(shortUrl);
        Assert.assertEquals(fullUrl,fullUrlByParse);
        log.info(String.format("测试获取短链响应 << [%s]", fullUrlByParse));


    }
}