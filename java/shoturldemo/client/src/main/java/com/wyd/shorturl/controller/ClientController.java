package com.wyd.shorturl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("shorturl")
public class ClientController {

    @Autowired
    private RedisTemplate template;

    @GetMapping("/get/{shorturl}")
    public String getUrl(String shorturl) {
        HashOperations hashOperations = template.opsForHash();
        String value = (String) hashOperations.get("shorturl:" + getCurrentCountry() + ":" + getCurrentUser(), shorturl);
        return value;
    }

    private String getCurrentUser() {
        return "";
    }

    private String getCurrentCountry() {
        return "";
    }
}
