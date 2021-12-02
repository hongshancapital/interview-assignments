package com.zhangliang.suril.controller;

import com.zhangliang.suril.controller.params.PostUrlParams;
import com.zhangliang.suril.controller.view.BaseResult;
import com.zhangliang.suril.service.ShortUrlService;
import com.zhangliang.suril.util.AssertUtils;
import com.zhangliang.suril.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短url控制器
 * <p>此控制器用来处理两个接口</p>
 * <ul>
 *   <li>提交长域名，获取短域名返回</li>
 *   <li>通过短域名查询长域名</li>
 * </ul>
 *
 * @author zhangliang
 * @mail to: 48906088@qq.com
 * @date 2021/12/01
 */
@RestController
@RequestMapping("/api")
@Validated
@Slf4j
@Api("短域名服务")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @PostMapping("/url")
    @ApiOperation("提交URL")
    public BaseResult<String> postUrl(@RequestBody PostUrlParams postUrl) {
        AssertUtils.isUrl(postUrl.getOriginalUrl());
        String shortUrl = shortUrlService.saveUrl(postUrl.getOriginalUrl());

        log.info("新请求到达" + "原始url：" + postUrl.getOriginalUrl() + " 新url：" + shortUrl);
        return ResultUtil.success(200, shortUrl);
    }

    @GetMapping("/url")
    @ApiOperation("获取URL")
    public BaseResult<String> getUrl(
            @RequestParam(required = true) @ApiParam(value = "提供短域名", required = true, name = "shortUrl") String shortUrl
    ) {
        AssertUtils.isUrl(shortUrl);
        String originalUrl = shortUrlService.getUrl(shortUrl);
        return ResultUtil.success(200, originalUrl);
    }
}
