package com.zhangzheng.homework.controller;

import com.zhangzheng.homework.controller.request.ShortUrlRequest;
import com.zhangzheng.homework.controller.response.WebResponse;
import com.zhangzheng.homework.service.ConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/4 下午8:52
 */
@Api(tags = "短url接口", produces = "application/json", consumes = "application/json")
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/url")
public class RestController {

    @Autowired
    private ConvertService convertService;

    @ApiOperation(value = "根据长url生成短url结果", notes = "长url")
    @PostMapping("/generate")
    public WebResponse generateShortUrl(@RequestBody ShortUrlRequest request) {
        return WebResponse.success(convertService.generate(request.getLongUrl()));
    }
    @ApiOperation(value = "根据短url还原长url结果", notes = "短url")
    @PostMapping("/revert")
    public WebResponse revert(@RequestBody ShortUrlRequest request) {
        return WebResponse.success(convertService.revertUrl(request.getShortUrl()));
    }
}
