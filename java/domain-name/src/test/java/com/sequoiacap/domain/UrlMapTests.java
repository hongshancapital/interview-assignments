package com.sequoiacap.domain;

import com.sequoiacap.domain.common.UrlMapStatus;
import com.sequoiacap.domain.entity.UrlMap;
import com.sequoiacap.domain.service.UrlMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 单元测试
 *
 * 测试覆盖率：走了正常情况的流程，不包含异常情况
 */
@SpringBootTest
class UrlMapTests {

    @Autowired
    private UrlMapService urlMapService;

    /**
     * 默认域名
     */
    @Value("${default.domain}")
    private String defaultDomain;

    /**
     * 测试结果：https://localhost:9099/sysmz7
     */
    @Test
    public void createUrlMap(){
        UrlMap urlMap = new UrlMap();
        urlMap.setUrlStatus(UrlMapStatus.AVAILABLE.getValue());
        urlMap.setLongUrl("https://www.kk.com/2012/21/31/asdfa/?11123123");
        urlMap.setDescription("测试短链");
        final String shortUrl = urlMapService.createUrlMap(defaultDomain, urlMap);
        System.out.println("测试结果：" + shortUrl);
    }

    /**
     * 测试结果：https://www.kk.com/2012/21/31/asdfa/?11123123
     */
    @Test
    public void getUrlMap(){
        String compressionCode = "shZiZA";
        final UrlMap urlMap = urlMapService.getUrlMap(compressionCode);
        System.out.println("测试结果：" + urlMap.getLongUrl());
    }
}
