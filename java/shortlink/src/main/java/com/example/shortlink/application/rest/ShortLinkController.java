package com.example.shortlink.application.rest;

import com.example.shortlink.application.response.ShortLinkResponse;
import com.example.shortlink.infrastructure.common.BizException;
import com.example.shortlink.domain.entity.ShortLink;
import com.example.shortlink.domain.service.ShortLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "短链服务")
public class ShortLinkController {
    private static final String URL_PREFIX = "https://short.co/s/";
    private final ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService service) {
        this.shortLinkService = service;
    }


    @ApiOperation("添加短链接口")
    @PostMapping(value = "/s/")
    @ResponseBody
    public ShortLinkResponse genShortLink(@RequestParam(required = true) String shortLinkKey) {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(shortLinkKey)) {
            throw new BizException("the content is not a url" + shortLinkKey, "11010");
        }
        ShortLink fromBackEnd = shortLinkService.genShortLink(shortLinkKey);
        ShortLinkResponse result = ShortLinkResponse.builder().build();
        result.setShortLink(URL_PREFIX + fromBackEnd.getShortLinkKey());
        result.setLongLink(fromBackEnd.getLongLink());
        return result;
    }

    @ApiOperation("获取短链接口")
    @RequestMapping(value = "/s/{shortLinkKey}", method = RequestMethod.GET)
    public ShortLinkResponse getShortLink(@PathVariable String shortLinkKey) {
        String sourceUrl = shortLinkService.getSourceLink(shortLinkKey);
        if (sourceUrl == null || Strings.isBlank(sourceUrl)) {
            return ShortLinkResponse.builder().build();
        }
        ShortLinkResponse result = ShortLinkResponse.builder().build();
        result.setShortLink(URL_PREFIX + shortLinkKey);
        result.setLongLink(sourceUrl);
        return result;
    }

}
