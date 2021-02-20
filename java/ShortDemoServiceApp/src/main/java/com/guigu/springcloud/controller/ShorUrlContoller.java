package com.guigu.springcloud.controller;

import com.guigu.springcloud.Util.ResultUtil;
import com.guigu.springcloud.entities.CommonResult;
import com.guigu.springcloud.service.ShortenLengthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@RestController
@Slf4j
@RequestMapping("/short")
public class ShorUrlContoller {

    @Resource
    private ShortenLengthService shortenLengthService;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value="/transferShortUrl",method = RequestMethod.GET)
    public CommonResult<String> transferShortUrl(@RequestParam("url") String url,@RequestParam("suffix")  String suffix) {
        System.out.println("=============");
        String shortUrl =  shortenLengthService.transferShortUrl(url,suffix);
        if(shortUrl!=null){
            return new ResultUtil<String>().setData(shortUrl);
        }else{
            return new ResultUtil<String>().setError("错误");
        }

    }
    @RequestMapping(value="/getOriginalUrl",method = RequestMethod.GET)
    public CommonResult<String> getOriginalUrl(@RequestParam("dUrl") String url) {
        System.out.println("========="+this.serverPort);
        String shortUrl =  shortenLengthService.getOriginalUrl(url);
        if(shortUrl!=null){
            return new ResultUtil<String>().setData(shortUrl);
        }else{
            return new ResultUtil<String>().setError("错误");
        }

    }

    @RequestMapping(value="/getSoket",method = RequestMethod.GET)
    public String getSoket(){
        return serverPort;
    }
}
