package com.scdt.web.api;

import com.scdt.domin.BaseResult;
import com.scdt.exception.BusinessException;
import com.scdt.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * ShortLinkController
 *
 * @author weixiao
 * @date 2022-04-26 11:33
 */
@Api(tags = "短域名服务接口")
@Validated
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ShortLinkController {

    @Autowired
    private LinkService linkService;

    @ApiOperation(value = "生成短域名", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/short-link")
    public BaseResult createShortLink(@RequestParam @NotBlank(message = "参数不能为空")
                                              String longLink) throws BusinessException {
        return BaseResult.success(linkService.createShortLink(longLink));
    }

    @ApiOperation(value = "获取长域名", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/long-link")
    public BaseResult getLongLink(@RequestParam @Length(min = 8, max = 8, message = "参数长度非法")
                                          String shortLink) throws BusinessException {
        return BaseResult.success(linkService.getLongLink(shortLink));
    }
}
