package org.hkm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hkm.model.ShortUrl;
import org.hkm.service.ShortApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

@RestController
@RequestMapping
@Api("转换短链接")
public class ShortApiController {


    private ShortApiService shortApiService;

    public ShortApiController(ShortApiService shortApiService){
        this.shortApiService = shortApiService;
    }

    @GetMapping("/{key}")
    @ApiOperation("获取原始链接")
    public ResponseEntity<String> redirect(@PathVariable("key") String key) {
        try {
            String url = shortApiService.redirect(key);
            if (url == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(URLEncoder.encode(url,"UTF-8"), HttpStatus.OK);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ApiOperation("转换链接")
    public ResponseEntity<String> transfer(@RequestBody ShortUrl url){
        try {
            String key = shortApiService
                    .transfer(Optional.ofNullable(URLDecoder.decode(url.getOriginUrl(), "UTF-8")).orElseThrow(() -> new ValidationException("request body can not be null")));
            if (key != null) {
                return new ResponseEntity<>(URLEncoder.encode(key, "UTF-8"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.TOO_MANY_REQUESTS);
    }

}
