package com.domain.url.web.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 心跳探活
 */
@RestController
@Api("心跳探活controller")
public class PingController {
    @GetMapping("ping")
    public String ping() {
        return "ok";
    }
}
