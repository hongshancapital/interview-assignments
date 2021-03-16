package com.sequoiacap.domain.api;

import com.sequoiacap.domain.common.UrlMapStatus;
import com.sequoiacap.domain.common.request.CreateUrlMapRequest;
import com.sequoiacap.domain.common.response.CreateUrlMapResponse;
import com.sequoiacap.domain.common.response.GetUrlMapResponse;
import com.sequoiacap.domain.common.response.Response;
import com.sequoiacap.domain.entity.UrlMap;
import com.sequoiacap.domain.service.UrlMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("域名转换api")
@RequestMapping("/domain/")
public class DomainNameApi {

    /**
     * 默认域名
     */
    @Value("${default.domain}")
    private String defaultDomain;

    @Autowired
    private UrlMapService urlMapService;

    @ApiOperation(value="获取长链接")
    @RequestMapping("/getUrlMap/{compressionCode}")
    public Response<GetUrlMapResponse> getUrlMap(@PathVariable("compressionCode") String compressionCode) {
        final UrlMap urlMap = urlMapService.getUrlMap(compressionCode);
        return Response.succeed(new GetUrlMapResponse(urlMap.getLongUrl()));
    }

    @ApiOperation(value="长域名转换为短域名")
    @RequestMapping("/createUrlMap")
    public Response<CreateUrlMapResponse> createUrlMap(CreateUrlMapRequest request) {
        UrlMap urlMap = new UrlMap();
        urlMap.setUrlStatus(UrlMapStatus.AVAILABLE.getValue());
        urlMap.setLongUrl(request.getLongUrl());
        urlMap.setDescription(request.getDescription());
        String shortUrl = urlMapService.createUrlMap(defaultDomain, urlMap);
        return Response.succeed(new CreateUrlMapResponse(shortUrl));
    }
}
