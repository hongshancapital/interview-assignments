package com.ttts.urlshortener.controller;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.model.BaseResult;
import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import com.ttts.urlshortener.domain.LUrlReq;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.domain.ShortUrlVO;
import com.ttts.urlshortener.service.ShortUrlCreateService;
import com.ttts.urlshortener.service.UrlQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/surl")
@Api(value = "url接口")
public class UrlController {

    private ShortUrlCreateService shortUrlCreateService;

    @Autowired
    public UrlController(ShortUrlCreateService shortUrlCreateService) {
        this.shortUrlCreateService = shortUrlCreateService;
    }


    @ApiOperation(value = "短链接生成")
    @PostMapping("/create")
    public BaseResult<ShortUrlVO> shorter(@RequestBody LUrlReq req) {
        ShortUrlVO record = shortUrlCreateService.create(req);
        if (record != null) {
            return BaseResult.success(record);
        } else {
            throw BusinessException.of(BaseResultCodeEnums.URL_CREATE_FAIL);
        }
    }
}
