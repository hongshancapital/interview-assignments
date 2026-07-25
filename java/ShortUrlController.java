package com.example.homework.controller;

import java.util.HashMap;
import java.util.Random;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("ShortUrlController")
@RequestMapping("/shortUrl")
@RestController
public class ShortUrlController {
	private String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 短链url域名前缀
    private String shortUrlPrefix = "http://a.cn/";
    private HashMap<String, String> map = new HashMap<>();

    @ApiOperation(value = "getShortUrl",notes = "getShortUrl")
    @PostMapping("/getShortUrl")
    public String getShortUrl(@RequestParam(value = "longUrl") String longUrl) {
        String key = creatKey();
        while (map.containsKey(key)) {
            key = creatKey();
        }
        map.put(key, longUrl);
        return shortUrlPrefix + key;
    }

    @ApiOperation(value = "getLongUrl",notes = "getLongUrl")
    @PostMapping("/getLongUrl")
    public String getLongUrl(@RequestParam(value = "shortUrl") String shortUrl) {
        return map.get(shortUrl.replace(shortUrlPrefix, ""));
    }

    private String creatKey() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(BASE62.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }

}
