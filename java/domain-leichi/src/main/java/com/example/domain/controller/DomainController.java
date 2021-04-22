package com.example.domain.controller;

import com.example.domain.util.ShortDomainGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 域名控制器
 *
 * @author 雷池
 */
@RestController
@RequestMapping("/domains")
@Slf4j
@Api(value = "/domains", tags = "短域名服务")
public class DomainController {

    /**
     * 短域名到长域名的映射，没有冲突，不用考虑并发
     */
    private static final Map<String, String> MAPPING = new HashMap<>();

    /**
     * 检测长域名唯一性
     */
    private static final Set<String> LONG_DOMAIN_SET = new HashSet<>();

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longDomain 长域名
     * @return 短域名
     */
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息", httpMethod = "POST",
            consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "longDomain", value = "长域名", required = true, dataTypeClass = String.class),
    })
    public String save(@RequestBody String longDomain) {
        Objects.requireNonNull(longDomain, "长域名不能为空");
        Assert.isTrue(LONG_DOMAIN_SET.add(longDomain), "长域名已存在");
        String shortDomain = ShortDomainGenerator.generate();
        MAPPING.put(shortDomain, longDomain);
        return shortDomain;
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param shortDomain 短域名
     * @return 长域名
     */
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息", httpMethod = "GET",
             produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "shortDomain", value = "短域名", required = true, dataTypeClass = String.class),
    })
    public String read(@RequestParam String shortDomain) {
        return MAPPING.get(shortDomain);
    }

    /**
     * 异常处理
     *
     * @param e 异常对象
     * @return e.getMessage
     */
    @ExceptionHandler
    public String handleAll(Exception e) {
        log.error("出现异常", e);
        return e.getMessage();
    }
}
