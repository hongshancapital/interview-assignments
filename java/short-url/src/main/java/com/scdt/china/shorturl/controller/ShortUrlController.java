package com.scdt.china.shorturl.controller;

import cn.hutool.core.util.StrUtil;
import com.scdt.china.shorturl.entity.LongUrlReq;
import com.scdt.china.shorturl.entity.common.R;
import com.scdt.china.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 短域名控制器
 *
 * @author：costa
 * @date：Created in 2022/4/11 17:45
 */
@Slf4j
@Api(value = "shortUrl", tags = "短域名模块")
@RestController
@RequiredArgsConstructor
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @ApiOperation(value = "长域名生成短域名")
    @PostMapping("/setShortUrl")
    @SuppressWarnings("rawtypes")
    public R setShortUrl(@RequestBody @Valid LongUrlReq request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.failed(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String shortCode = shortUrlService.getShortUrl(request.getUrl());
        return shortCode != null ?  R.ok(shortCode): R.failed("请求失败,请重试");
    }

    @ApiOperation(value = "短域名获取长域名")
    @GetMapping("/getUrl/{shortCode}")
    @SuppressWarnings("rawtypes")
    public R getUrl(@PathVariable(name = "shortCode") String shortCode) {
        if (StrUtil.isBlank(shortCode)) {
            return R.failed("参数不能为空");
        }
        String url = shortUrlService.getUrl(shortCode);
        return url != null ? R.ok(url) : R.failed("该链接不存在或已失效");
    }
}
