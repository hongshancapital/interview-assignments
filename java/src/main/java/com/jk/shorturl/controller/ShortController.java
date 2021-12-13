package com.jk.shorturl.controller;

import com.jk.shorturl.config.ConfigMainUtil;
import com.jk.shorturl.controller.bean.ResponseEntity;
import com.jk.shorturl.service.ShortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短域名服务
 *
 * @author Jiang Jikun
 */
@RequestMapping("/api/short")
@RestController
@Api(tags = "域名转换服务", description = "长域名和短域名互相转换接口。")
@Slf4j
public class ShortController {

    @Resource
    ShortService shortService;

    @ApiOperation(value = "获取短域名", response = String.class,
            notes = "传入长域名信息，返回短域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longURL", value = "长域名信息", dataType = "string", required = true, paramType = "query", example = "http://www.demo.com/xx")
    })
    @PostMapping("/short")
    public ResponseEntity getShortURL(String longURL) {
        if (!StringUtils.hasLength(longURL)) {
            return ResponseEntity.failure("请传入长域名参数，参数名为：longURL");
        }
        //log.info("i want get short url,i am {}",longURL);
        String shortURL = shortService.generalShortURL(longURL);
        //log.info("i have get a short url ,is {},i am {}", shortURL, longURL);
        return ResponseEntity.success(shortURL);
    }

    @ApiOperation(value = "获取长域名", response = String.class,
            notes = "传入短域名信息，返回长域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortURL", value = "短域名信息", dataType = "string", required = true, paramType = "query", example = "http://t.cn/xyxy")
    })
    @GetMapping("/long")
    public ResponseEntity getLongURL(String shortURL) {
        if (!StringUtils.hasLength(shortURL)) {
            return ResponseEntity.failure("请传入短域名参数，参数名为：shortURL");
        }

        String shortCode = ConfigMainUtil.getCodeFromURL(shortURL);
        String longURL = shortService.getLongURL(shortCode);
        if (StringUtils.hasLength(longURL)) {
            return ResponseEntity.success(longURL);
        } else {
            return ResponseEntity.failure("没有找到对应的长域名");
        }
    }

}
