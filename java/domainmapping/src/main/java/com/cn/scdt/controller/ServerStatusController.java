package com.cn.scdt.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@Api(value = "公共接口", tags = { "公共接口" })
@Slf4j
public class ServerStatusController {

    @Value("${app.version}")
    private String version;

    @Value("${app.build.time}")
    private String buildTime;

    @GetMapping("/serverstatus")
    @ApiOperation(value = "服务状态查询", notes = "")
    public Object getStatus(){
        Map<String, Object> result = new HashMap<>(4);
        result.put("status", "UP");
        result.put("now", LocalDateTime.now());
        result.put("version", version);
        result.put("buildTime", buildTime);
        return result;

    }
}
