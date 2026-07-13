package com.example.sequoiahomework.controller;

import com.example.sequoiahomework.common.response.DataResult;
import com.example.sequoiahomework.common.utils.UrlUtils;
import com.example.sequoiahomework.service.ChangeUrlService;
import com.example.sequoiahomework.vo.url.ChangeUrlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Irvin
 * @description 改变url的接口类
 * @date 2021/10/9 19:38
 */
@RestController
@RequestMapping("/change/url")
@Api(value = "改变url的接口类", tags = {"改变url的接口类"})
public class ChangeUrlController {

    @Resource
    private ChangeUrlService changeUrlService;

    /**
     * 长链接转短链接
     *
     * @param changeUrlVo 请求数据体
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.String>
     * @author Irvin
     * @date 2021/10/9
     */
    @PostMapping("/lts")
    @ApiOperation(value = "长链接转短链接")
    //@PreventSubmit
    public DataResult<String> longToShort(@RequestBody @Validated ChangeUrlVo changeUrlVo) {
        String originalUrl = changeUrlVo.getOriginalUrl();
        if (!UrlUtils.isUrl(originalUrl)) {
            return new DataResult<>(0, 300, null, "入参并非有效网址");
        }
        String shortUrl = changeUrlService.longToShort(changeUrlVo.getOriginalUrl());
        return new DataResult<>(0, 200, shortUrl, "转换成功");
    }


    /**
     * 根据短链接获得长链接
     *
     * @param changeUrlVo 请求数据体
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.String>
     * @author Irvin
     * @date 2021/10/9
     */
    @PostMapping("/stl")
    @ApiOperation(value = "根据短链接获得长链接")
    public DataResult<String> shortToLong(@RequestBody @Validated ChangeUrlVo changeUrlVo) {
        String longUrl = changeUrlService.shortToLong(changeUrlVo.getOriginalUrl());
        if (StringUtils.isBlank(longUrl)) {
            return new DataResult<>(0, 300, null, "未能匹配到相应的长链接");
        }
        return new DataResult<>(1, 200, longUrl, "查询成功");
    }

}
