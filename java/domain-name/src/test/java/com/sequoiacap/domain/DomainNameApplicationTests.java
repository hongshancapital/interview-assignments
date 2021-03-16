package com.sequoiacap.domain;

import com.alibaba.fastjson.JSONObject;
import com.sequoiacap.domain.api.DomainNameApi;
import com.sequoiacap.domain.common.UrlMapStatus;
import com.sequoiacap.domain.common.request.CreateUrlMapRequest;
import com.sequoiacap.domain.common.response.CreateUrlMapResponse;
import com.sequoiacap.domain.common.response.Response;
import com.sequoiacap.domain.entity.UrlMap;
import com.sequoiacap.domain.service.UrlMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;


@SpringBootTest
class DomainNameApplicationTests {

    @Autowired
    private MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    private UrlMapService urlMapService;

    @Autowired
    private DomainNameApi domainNameApi;

    @Test
    void contextLoads() {
        String domain = "localhost:9099";
        UrlMap urlMap = new UrlMap();
        urlMap.setUrlStatus(UrlMapStatus.AVAILABLE.getValue());
        urlMap.setLongUrl("https://throwx.cn/2020/08/24/canal-ha-cluster-guide");
        urlMap.setDescription("测试短链");
        String url = urlMapService.createUrlMap(domain, urlMap);
        System.out.println(url);
    }

    @Test
    public void createUrlMap(){
        CreateUrlMapRequest request = new CreateUrlMapRequest();
        request.setLongUrl("https://www.baidu.com/200093/1231/canal");
        final Response<CreateUrlMapResponse> response = domainNameApi.createUrlMap(request);
        System.out.println(JSONObject.toJSONString(response));
    }
}
