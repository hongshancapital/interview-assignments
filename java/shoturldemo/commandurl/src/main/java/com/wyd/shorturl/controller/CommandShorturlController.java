package com.wyd.shorturl.controller;


import com.wyd.shorturl.controller.parameter.ShorturlParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command/shorturl")
public class CommandShorturlController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/add")
    public String command(@RequestBody ShorturlParameter url) throws Exception {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("shorturl:" + getCurrentCountry() + ":" + getCurrentUser(),
                SHA1.generate(url.getUrl().getBytes()), url.getUrl());
        return "ok";
    }

    private String getCurrentUser() {
        return "";
    }

    private String getCurrentCountry() {
        return "";
    }
}
