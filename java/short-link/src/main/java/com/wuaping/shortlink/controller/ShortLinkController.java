package com.wuaping.shortlink.controller;

import com.wuaping.shortlink.model.RestResult;
import com.wuaping.shortlink.model.request.ShortLinkRequest;
import com.wuaping.shortlink.model.response.OriginalLinkResponse;
import com.wuaping.shortlink.model.response.ShortLinkResponse;
import com.wuaping.shortlink.service.ShortLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 短域名控制器
 *
 * @author Aping
 * @since 2022/3/16 21:49
 */
@RestController
@RequestMapping("/link")
@Api(tags = "短域名业务")
@Slf4j
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @PostMapping("/short")
    @ApiOperation("生成短域名")
    public RestResult<ShortLinkResponse> toShortLink(@Validated @RequestBody ShortLinkRequest request) {
        log.info("[request] /link/short originalLink={}", request.getOriginalLink());

        String shortLink = shortLinkService.toShortLink(request.getOriginalLink());

        ShortLinkResponse response = new ShortLinkResponse(shortLink);

        return RestResult.success(response);
    }

    @GetMapping("/original")
    @ApiOperation("获取原域名")
    public RestResult<OriginalLinkResponse> originalLink(@ApiParam(value = "短域名") @RequestParam("shortLink") String shortLink) {
        log.info("[request] /link/original shortLink={}", shortLink);

        String originalLink = shortLinkService.originalLink(shortLink);

        OriginalLinkResponse response = new OriginalLinkResponse(originalLink);

        return RestResult.success(response);
    }
}
