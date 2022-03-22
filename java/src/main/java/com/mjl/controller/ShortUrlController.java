package com.mjl.controller;

import com.mjl.api.ShortUrlService;
import com.mjl.model.GetLongUrlRequest;
import com.mjl.model.GetShortUrlRequest;
import com.mjl.model.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Api
public class ShortUrlController {

    private ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }


    @ApiOperation(value = "获取短链接", notes = "输入长链接，返回短链接")
    @PostMapping("/getShortUrl")
    public Response<String> getShortUrl(@RequestBody GetShortUrlRequest request){
        String shortUrl  = shortUrlService.generateShortUrl(request.getLongUrl());
        return Response.success(shortUrl);
    }

    @ApiOperation(value = "获取长链接", notes = "输入短链接后缀，返回长链接")
    @PostMapping("/getLongUrlBySuffix")
    public Response<String> getLongUrlBySuffix(@RequestBody GetLongUrlRequest request){
        String longUrl = shortUrlService.getLongUrlBySuffix(request.getShortUrlSuffix());
        return Response.success(longUrl);
    }

    @ApiOperation(value = "获取长链接", notes = "输入完整短链接，返回长链接")
    @PostMapping("/getLongUrl")
    public Response<String> getLongUrl(@RequestBody GetLongUrlRequest request){
        String longUrl = shortUrlService.getLongUrl(request.getShortUrl());
        return Response.success(longUrl);
    }
}
