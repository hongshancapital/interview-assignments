package com.example.surl.controller;

import cn.hutool.core.util.StrUtil;
import com.example.surl.entity.UrlDO;
import com.example.surl.service.UrlService;
import com.example.surl.utils.Ajax;
import com.example.surl.utils.RedisUtil;
import com.example.surl.utils.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * @author 杨欢
 */
@RestController
@Api(tags = "短链接接口")
public class UrlController {

    private final UrlService service;
    private final RedisUtil redisUtil;


    public UrlController(UrlService service, RedisUtil redisUtil) {
        this.service = service;
        this.redisUtil = redisUtil;
    }


    @PostMapping
    @ApiOperation(value = "生成短链接信息")
    @ApiImplicitParam(name = "url", value = "长链接", required = true, dataType = "string")
    public Ajax<UrlDO> createSurl(@RequestParam(name = "url")String url) {

        if (!UrlUtil.isValidUrl(url)) {
            throw new RuntimeException("url不合法");
        }

        String s = redisUtil.get(RedisUtil.preL + url);
        if (!StrUtil.isNullOrUndefined(s)) {
            return Ajax.success(new UrlDO(s, url));
        }
        UrlDO sUrl = this.service.createSUrl(url);

        return Ajax.success(sUrl);
    }

    @GetMapping("{surl}")
    @ApiOperation(value = "获取长链接信息")
    @ApiImplicitParam(name="surl", value = "短链接URI", required = true, dataType = "string", paramType = "path")
    public Ajax<UrlDO> getUrl(@PathVariable String surl) {
        if (StrUtil.length(surl) != 8) {
            throw new RuntimeException("短链接不合法");
        }

        String s = redisUtil.get(RedisUtil.preS + surl);
        if (!StrUtil.isNullOrUndefined(surl)) {
            return Ajax.success(new UrlDO(surl, s));
        }

        UrlDO urlDO = this.service.getUrl(s);
        return Ajax.success(urlDO);
    }
}
