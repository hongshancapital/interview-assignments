package com.hongshan.shorturl.controller;

import com.hongshan.shorturl.domain.dto.ShortUrlGenerateDTO;
import com.hongshan.shorturl.domain.model.ResultVO;
import com.hongshan.shorturl.domain.model.ShortUrlModel;
import com.hongshan.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 */
@RestController
@Api(value = "短链接服务", tags = "短链接服务")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;


    @PostMapping("/generate")
    @ApiOperation(value = "生成短链接", notes = "生成短链接")
    public ResultVO<ShortUrlModel> generate(@RequestBody @Valid ShortUrlGenerateDTO request) {
        ShortUrlModel shortUrlModel = shortUrlService.createShortUrl(request);
        return ResultVO.success(shortUrlModel);
    }

    @GetMapping("/{suffix}")
    @ApiImplicitParam(name = "suffix", value = "短链接后缀", required = true)
    @ApiOperation(value = "获取原始链接", notes = "获取原始链接")
    public ResultVO<ShortUrlModel> getOrigin(@PathVariable("suffix") String key, HttpServletResponse response) throws Exception {
        ShortUrlModel shortUrlModel = shortUrlService.getShorUrl(key);
        response.sendRedirect(shortUrlModel.getOriginUrl());
        return ResultVO.success(shortUrlModel);
    }
}
