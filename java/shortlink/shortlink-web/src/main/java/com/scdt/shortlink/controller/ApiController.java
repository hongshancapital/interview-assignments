package com.scdt.shortlink.controller;

import com.scdt.shortlink.client.dto.Result;
import com.scdt.shortlink.client.service.LinksService;
import com.scdt.shortlink.dto.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 使用短链获取原始常量接口
 *
 * @Author tzf
 * @Date 2022/4/28
 */
@Api(tags = "短链接口")
@RestController
public class ApiController {
    /**
     * 链接服务
     */
    @Resource
    private LinksService linksService;

    /**
     * 通过用户输入的短链，返回之前存储的长链
     * 测试样例：
     * http://localhost:8080/test
     *
     * @param shortLink
     * @return
     */
    @ApiOperation(value = "获取已存储的长链", notes = "通过入参短链，获取之前存储的长链")
    @RequestMapping(value = "/toShortLink/get", method = RequestMethod.GET)
    public ResponseDTO<String> getOriginalLink(
        @RequestParam(name = "shortLink") @ApiParam(value = "短链", required = true) String shortLink) {
        Result<String> result = linksService.getOriginalLink(shortLink);
        if (result.getIsSuccess() && result.getResult()) {
            return ResponseDTO.success(result.getModel());
        }
        return ResponseDTO.error(result.getErrorCode(), result.getErrorMessage());
    }

    /**
     * 保存长链与短链的映射关系
     *
     * @param link
     * @return
     */
    @ApiOperation(value = "生成短链", notes = "通过入参长链，生成对应的短链")
    @RequestMapping(value = "/toShortLink/save", method = RequestMethod.GET)
    public ResponseDTO<String> save(
        @RequestParam(name = "link") @ApiParam(value = "长链接", required = true) String link) {
        Result<String> result = linksService.getShortLink(link);
        if (result.getIsSuccess() && result.getResult()) {
            return ResponseDTO.success(result.getModel());
        }
        return ResponseDTO.error(result.getErrorCode(), result.getErrorMessage());
    }
}
