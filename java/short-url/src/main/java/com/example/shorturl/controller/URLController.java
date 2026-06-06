package com.example.shorturl.controller;

import com.example.shorturl.dao.URLMapping;
import com.example.shorturl.dao.ValidGroup;
import com.example.shorturl.service.URLService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Validated
public class URLController {

    @Autowired
    URLService urlService;

    @PostMapping(value = "/lurls")
    @ApiOperation(value = "短域名存储接口")
    @ApiImplicitParam(name = "lurl", value = "长链接", required = true,
            example = "https://new.qq.com/omn/20210913/20210913A01JME00.html")
    public String createShortURL(@Validated(value = ValidGroup.Crud.Create.class)
                                 @RequestBody URLMapping urlMapping) {
        String surlWithDomain = urlService.createShortURL(urlMapping);
        return surlWithDomain;
    }

    @GetMapping("/{surl}")
    @ApiOperation(value = "短域名读取接口")
    @ApiImplicitParam(name = "surl", value = "短链接字符码", required = true, example = "1234abcd")
    public ModelAndView redirect(@PathVariable String surl, ModelAndView mav) {
        String lurl = urlService.getLongURL(surl);
        // redirect to lurl
        mav.setViewName("redirect:" + lurl);
        return mav;

    }

}
