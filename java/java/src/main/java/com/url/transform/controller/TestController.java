package com.url.transform.controller;

import com.url.transform.common.response.RestResult;
import com.url.transform.generator.StringGeneratorRandom;
import com.url.transform.generator.UrlShorterGeneratorSimple;
import com.url.transform.shorter.ShorterString;
import com.url.transform.storage.ShorterStorageMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * @author xufei
 * @Description
 * @date 2021/12/10 10:21 AM
 **/
@Api("域名转换")
@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

  UrlShorterGeneratorSimple simple = new UrlShorterGeneratorSimple();
  ShorterStorageMemory memory = new ShorterStorageMemory<ShorterString>();

  @ApiOperation(value = "转换URL")
  @PostMapping(path = "transFormUrl")
  public RestResult transFormUrl(@RequestParam(value = "需要转换的URL") String url) {
    simple.setGenerator(new StringGeneratorRandom(8));
    simple.setShorterStorage(memory);
    String shorter = simple.generate(url).getShorter();
    return RestResult.success(shorter);
  }

  @ApiOperation(value = "转换shorter")
  @PostMapping(path = "transFormShorter")
  public RestResult transFormShorter(@RequestParam(value = "需要转换的shorter") String shorter) {
    if(memory.get(shorter)!=null){
      return RestResult.success(memory.get(shorter));
    }else{
      return RestResult.error("短链接不存在");
    }
  }

}
