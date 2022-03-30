package com.hongshan.shorturl.controller;

import com.hongshan.shorturl.model.reqs.ShortUrlGenRequest;
import com.hongshan.shorturl.model.resps.ResultVO;
import com.hongshan.shorturl.model.resps.ShortUrlResp;
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
 * @date: 2021/12/29
 * @description:
 */
@RestController
@Api(value = "短链接服务", tags = "短链接服务")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;


    @PostMapping("/generate")
    @ApiOperation(value = "生成短链接", notes = "生成短链接")
    public ResultVO<ShortUrlResp> generate(@RequestBody @Valid ShortUrlGenRequest request) {
        return ResultVO.success(shortUrlService.genShortUrl(request));
    }

    @GetMapping("/{suffix:[0-9a-zA-Z]{1,8}}")
    @ApiImplicitParam(name = "suffix", value = "短链后缀", required = true, dataType = "String")
    @ApiOperation(value = "获取原始链接", notes = "获取原始链接")
    public ResultVO<String> getOrigin(@PathVariable("suffix") String suffix, HttpServletResponse response) throws Exception {
        response.sendRedirect(shortUrlService.getShorUrl(suffix));
        return ResultVO.success("OK");
    }
}
