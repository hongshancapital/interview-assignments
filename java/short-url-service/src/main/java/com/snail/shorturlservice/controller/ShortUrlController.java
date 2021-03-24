package com.snail.shorturlservice.controller;

import com.snail.shorturlservice.common.response.ResultBean;
import com.snail.shorturlservice.service.ShortUrlService;
import com.snail.shorturlservice.vo.ShortenUrlReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/surl")
@Controller
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * 接收短链接，返回原始URL地址
     * @param shortUrl
     * @return
     */
    @ResponseBody
    @GetMapping("/{shortUrl}")
    public ResultBean<String> getLongUrl(@PathVariable("shortUrl") String shortUrl) {
        String url = this.shortUrlService.getLongUrl(shortUrl);
        return ResultBean.success(url);
    }

    /**
     * 接收URL地址，返回短链接
     * @param reqVO
     * @return
     */
    @ResponseBody
    @PostMapping("/shorten")
    public ResultBean<String> shorten(@Valid @RequestBody ShortenUrlReqVO reqVO) {
        String shortUrl = this.shortUrlService.shorten(reqVO.getUrl());
        return ResultBean.success(shortUrl);
    }
}
