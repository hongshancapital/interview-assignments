package com.guigu.springcloud.controller;


import com.guigu.springcloud.entities.CommonResult;
import com.guigu.springcloud.service.ShortUrlService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback ="errorMethod")
@RequestMapping("/short")
public class ShortUrlConttoller {

    @Resource
    private ShortUrlService shortUrlService;


    @Value("${server.port}")
    private String serverPort;

    @HystrixCommand(fallbackMethod = "timeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "26000")
    })
    @RequestMapping(value="/transferShortUrl",method = RequestMethod.GET)
    public CommonResult transferShortUrl(@RequestParam("url") String  url,@RequestParam("suffix") String  suffix)
    {
        return shortUrlService.transferShortUrl(url,suffix);
    }

    @RequestMapping(value="/getOriginalUrl",method = RequestMethod.GET)
    public CommonResult getOriginalUrl(@RequestParam("dUrl") String  dUrl)
    {
        return shortUrlService.getOriginalUrl(dUrl);
    }

    public CommonResult timeOutHandler(@RequestParam("url")  String url,@RequestParam("suffix") String  suffix){
        return new CommonResult(200,"出错了");
    }

    public CommonResult errorMethod(){
        return new CommonResult(200,"访问出错了");
    }


    @RequestMapping(value="/getPort",method = RequestMethod.GET)
    public String getPortP(){
        System.out.println("============");
         return serverPort;
    }
}
