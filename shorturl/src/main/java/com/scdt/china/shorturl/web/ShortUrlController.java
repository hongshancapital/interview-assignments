package com.scdt.china.shorturl.web;

import com.scdt.china.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags="短链接口")
@Controller
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("生成短链")
    @ResponseBody
    @RequestMapping("/getShortUrl")
    public String getShortUrl(String url) {
        return shortUrlService.generateShortUrl(url);
    }

    @ApiOperation("获取长链")
    @ResponseBody
    @RequestMapping("/getUrl")
    public String getUrl(String shortUrl) {
        return shortUrlService.fetchUrl(shortUrl);
    }

}
