package com.samples.urlshortener.controller;

import com.google.common.hash.Hashing;
import com.samples.urlshortener.common.ErrorCodeEnum;
import com.samples.urlshortener.common.Result;
import com.samples.urlshortener.configuration.LocalCacheManager;
import com.samples.urlshortener.exception.InvalidUrlException;

import java.nio.charset.StandardCharsets;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * URL Shortener Resource
 *
 * @author liuqu
 */
@Slf4j
@RestController
@Api(tags = "短链接")
@RequestMapping(path = "/v1")
public class UrlShortenerController {

  /** 基于guava的内存缓存 */
  @Autowired
  private LocalCacheManager localCacheManager;


  @ApiOperation(value = "根据输入的长链接生成短链接 ", notes = "根据输入的长链接生成短链接")
  @GetMapping(path = "/createShortUrl")
  public Result<String> createShortUrlByLongUrl(@RequestParam("longUrl") String longUrl) {
    if(!StringUtils.isEmpty(longUrl)){
      UrlValidator urlValidator = new UrlValidator(
              new String[]{"http", "https"}
      );
      if (urlValidator.isValid(longUrl)) {
        String shortUrl = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
        log.debug("URL Id generated: {}", shortUrl);
        localCacheManager.setShortLongMap(shortUrl, longUrl);
        return Result.ok(shortUrl);
      }
    }
    return Result.fail("param invalid!");
  }

  @ApiOperation(value = "根据短链接获取原始长链接 ", notes = "根据短链接获取原始长链接")
  @GetMapping(path = "/getLongUrl")
  public Result<String> getLongUrl(@RequestParam("shortUrl") String shortUrl) {
    if(!StringUtils.isEmpty(shortUrl)){
       String longUrl = localCacheManager.getLongUrlByShortUrl(shortUrl);
       if(!StringUtils.isEmpty(longUrl)){
         return Result.ok(longUrl);
       }else {
         return Result.fail("url is not exists or lose efficacy！");
       }
    }
    return Result.fail("param is error");
  }
}
