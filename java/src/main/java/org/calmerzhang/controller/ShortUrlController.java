package org.calmerzhang.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.calmerzhang.common.exception.BusinessException;
import org.calmerzhang.controller.dto.ReturnEntity;
import org.calmerzhang.service.api.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * 短链接口
 *
 * @author calmerZhang
 * @create 2022/01/06 8:45 下午
 */
@RestController
@Slf4j
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping(path = "/" +
            "" +
            "")
    @ApiOperation(value = "生成短域名",notes = "请输入完整域名，返回短域名")
    public ReturnEntity<String> getShortUrl(@RequestParam("longUrl") @NotEmpty String longUrl) {
        try {
            return ReturnEntity.success(shortUrlService.getShortUrl(longUrl));
        } catch (BusinessException e) {
            log.error("getShortUrl error : ", e);
            return ReturnEntity.error(e.getCode(), e.getMsg());
        }
    }

    @GetMapping(path = "/longUrl")
    @ApiOperation(value = "获取完整域名",notes = "输入短域名，返回完整域名")
    public ReturnEntity<String> getLongUrl(@RequestParam("shortUrl") @NotEmpty String shortUrl) {
        return ReturnEntity.success(shortUrlService.getLongUrl(shortUrl));
    }
}
