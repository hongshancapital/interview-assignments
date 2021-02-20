package com.guigu.springcloud.service;


import com.guigu.springcloud.entities.CommonResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ShortFallBackService implements ShortUrlService {

    @Override
    public CommonResult<String> transferShortUrl(@RequestParam("url") String url,@RequestParam("suffix") String suffix) {
        return new CommonResult<String>(404,"出错了呀");
    }

    @Override
    public CommonResult<String> getOriginalUrl(String dUrl) {
        return new CommonResult<String>(404,"出错了呀");
    }
}
