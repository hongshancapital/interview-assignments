package com.sequoia.interview.shorturl.restful;

import java.io.IOException;

import com.sequoia.interview.shorturl.service.ShortUrlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author james xu <james4xu@aliyun.com>
 */
@Api(tags = "短网址API")
@RestController
public class ShortUrlController {
  private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

  @Autowired
  private ShortUrlService shortUrlService;

  @ApiOperation(value = "genShortUrl")
  @GetMapping("/api/shorturl/gen")
  @ResponseBody
  public String genShortUrl(@ApiParam(required = true) @RequestParam("url") String url) throws IOException {
    String shortUrl;
    shortUrl = shortUrlService.genShortUrl(url);
    //logger.debug("url:" + url + ", shortUrl:" + shortUrl);
    return shortUrl;
  }

  @ApiOperation(value = "getUrl")
  @GetMapping("/api/shorturl/geturl")
  @ResponseBody
  public String getUrl(@ApiParam(required = true) @RequestParam("s") String shortUrl) throws IOException {
    String url;
    url = shortUrlService.getUrl(shortUrl);
    return url;
  }

}
