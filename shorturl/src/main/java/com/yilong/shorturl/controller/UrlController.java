package com.yilong.shorturl.controller;

import com.yilong.shorturl.model.Result;
import com.yilong.shorturl.model.dto.UrlDTO;
import com.yilong.shorturl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping(value = "encode")
    public Result<String> encodeUrl(@Valid @RequestBody UrlDTO urlDTO) {
        return Result.Builder.success(urlService.saveOriginUrl(urlDTO.getUrl()));
    }

    @PostMapping(value = "decode")
    public Result<String> decodeUrl(@Valid @RequestBody UrlDTO urlDTO) {
        return Result.Builder.success(urlService.getOriginUrlByShort(urlDTO.getUrl()));
    }
}
