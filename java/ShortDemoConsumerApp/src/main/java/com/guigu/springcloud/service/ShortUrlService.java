package com.guigu.springcloud.service;

import com.guigu.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value="CLOUD-SHORTURL-SERVICE",fallback = ShortFallBackService.class)
public interface ShortUrlService {

    @RequestMapping(value = "/short/transferShortUrl")
    public CommonResult<String> transferShortUrl(@RequestParam("url") String url,@RequestParam("suffix") String suffix) ;

    @RequestMapping(value = "/short/getOriginalUrl")
    public CommonResult<String> getOriginalUrl(@RequestParam("dUrl") String dUrl) ;
}
