package com.zj.domainservice.controller;

import com.zj.domainservice.util.LruLinkedHashMap;
import com.zj.domainservice.util.NumericConvertUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Api(tags = "SpringBoot集成Swagger2测试")
@RestController
public class DomainController {
    private final ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    //可以避免内存溢出，并发不行

    private final LruLinkedHashMap<String, String> linkedHashMap = new LruLinkedHashMap<String, String>(10000);

    @ApiOperation(value = "获取短域名", notes = "根据longDomain获取短域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "longDomain", value = "长域名信息", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(response = String.class, message = "返回短域名信息", code = 200),
    })
    @RequestMapping("/getShortDomain")
    public String getShort(String longDomain) {

        if (longDomain == null) return "请输入合法的值!!!";

        String shortDomain = NumericConvertUtils.getShort(longDomain);
        concurrentHashMap.putIfAbsent(shortDomain, longDomain);

        //可以避免内存溢出，并发不行
//        synchronized (this) {
//            linkedHashMap.putIfAbsent(shortDomain, longDomain);
//        }

        return NumericConvertUtils.getShort(longDomain);
    }

    @RequestMapping("/getLongDomain")
    public String getLong(String shortDomain) {
        if (shortDomain == null) return "请输入合法的值!!!";
        //可以避免内存溢出，并发不行
//        synchronized (this) {
//            return linkedHashMap.get(shortDomain);
//        }
        return concurrentHashMap.get(shortDomain);
    }
}
