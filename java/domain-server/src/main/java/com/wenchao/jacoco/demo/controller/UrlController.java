package com.wenchao.jacoco.demo.controller;

import com.wenchao.jacoco.demo.service.UrlService;
import com.wenchao.jacoco.demo.utils.Ret;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * @author Wenchao Gong
 * @date 2021/12/15 16:10
 */
@Api(tags = "域名转换接口集")
@Validated
@RestController
@RequestMapping("/url")
public class UrlController {

    @Resource
    private UrlService urlService;

    @GetMapping("/to-short")
    @ApiOperation("长域名转换为短域名")
    public Ret<String> toShort(@ApiParam(value = "长域名", required = true)
                                   @RequestParam("longUrl") String longUrl) {
        return Ret.ok(urlService.getShortUrl(longUrl));
    }

    @GetMapping("/to-long")
    @ApiOperation("短域名转换为长域名")
    public Ret<String> toLong(@ApiParam(value = "短域名", required = true)
                                  @Pattern(regexp = "^\\w+$", message = "短域名格式不正确")
                                  @RequestParam("shortUrl") String shortUrl) {
        Optional<String> result = urlService.getLongUrl(shortUrl);
        return result.isPresent() ? Ret.ok(result.get()) : Ret.error("短域名不存在");
    }
}
