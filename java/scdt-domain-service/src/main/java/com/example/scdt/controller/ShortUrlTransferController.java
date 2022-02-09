package com.example.scdt.controller;

import com.example.scdt.exception.BusinessException;
import com.example.scdt.service.IShortUrlTransferService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JonathanCheung
 * @date 2021/12/11 17:29
 * @describe
 */
@RestController
@RequestMapping("/url")
public class ShortUrlTransferController {

    @Autowired
    private IShortUrlTransferService shortUrlTransferService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrl 长域名
     * @return 短域名
     */
    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @ApiImplicitParam(name = "longUrl", value = "长域名", required = true, dataType = "String")
    @GetMapping("/longUrlToShortUrl")
    public String longUrlToShortUrl(@RequestParam String longUrl) {
        return shortUrlTransferService.longUrlToShortUrl(longUrl);
    }


    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param shortUrl 短域名
     * @return 长域名
     */
    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", required = true, dataType = "String")
    @GetMapping("/shortUrlToLongUrl")
    public String shortUrlToLongUrl(@RequestParam String shortUrl) throws BusinessException {
        return shortUrlTransferService.shortUrlToLongUrl(shortUrl);
    }
}
