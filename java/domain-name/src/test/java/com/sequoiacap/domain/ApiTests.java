package com.sequoiacap.domain;

import com.alibaba.fastjson.JSONObject;
import com.sequoiacap.domain.api.DomainNameApi;
import com.sequoiacap.domain.common.UrlMapStatus;
import com.sequoiacap.domain.common.request.CreateUrlMapRequest;
import com.sequoiacap.domain.common.response.CreateUrlMapResponse;
import com.sequoiacap.domain.common.response.GetUrlMapResponse;
import com.sequoiacap.domain.common.response.Response;
import com.sequoiacap.domain.entity.UrlMap;
import com.sequoiacap.domain.service.UrlMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * api测试
 */
@SpringBootTest
class ApiTests {

    private MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    private DomainNameApi domainNameApi;

    /**
     * 默认域名
     */
    @Value("${default.domain}")
    private String defaultDomain;

    /**
     * 测试结果：{"code":200,"payload":{"shortUrl":"https://localhost:9099/sEfgdN"}}
     */
    @Test
    public void createUrlMap() {
        CreateUrlMapRequest request = new CreateUrlMapRequest();
        request.setLongUrl("https://www.kk.com/2012/21/31/asdfa/fffdddeee");
        final Response<CreateUrlMapResponse> urlMap = domainNameApi.createUrlMap(request);
        System.out.println("测试结果：" + JSONObject.toJSONString(urlMap));
    }

    /**
     * 测试结果：https://www.kk.com/2012/21/31/asdfa/fffdddeee
     */
    @Test
    public void getUrlMap() {
        String compressionCode = "sEfgdN";
        final Response<GetUrlMapResponse> response = domainNameApi.getUrlMap(compressionCode);
        System.out.println("测试结果：" + response.getPayload().getLongUrl());
    }
}
