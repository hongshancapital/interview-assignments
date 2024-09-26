package com.example.shorturl.controller;

import com.example.shorturl.bean.GetOriginalUrlRequestVO;
import com.example.shorturl.bean.GetShortUrlRequestVO;
import com.example.shorturl.bean.ResponseDTO;
import com.example.shorturl.manager.ShortUrlManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@Api(tags = "短域名")
@RestController
@Slf4j
public class ShortUrlController {

    @Autowired
    ShortUrlManager shortUrlManager;

    @PostMapping("/getShortUrl")
    @ApiOperation("通过原始域名获取短域名")
    public ResponseDTO<String> getShortUrl(@RequestBody GetShortUrlRequestVO requestVO) {
        final String originalUrl = requestVO.getOriginalUrl();
        log.info("getShortUrl, originalUrl: {}", originalUrl);
        try {
            if (StringUtils.isEmpty(originalUrl)) {
                return ResponseDTO.error("original url is null");
            }

            String shortUrl = shortUrlManager.getShortUrl(originalUrl);
            return ResponseDTO.success(shortUrl);
        } catch (Exception e) {
            log.error("getShortUrl error", e);
            return ResponseDTO.error("getShortUrl error:" + e.getMessage());
        }

    }

    @PostMapping("/getOriginalUrl")
    @ApiOperation("通过短域名获取原始域名")
    public ResponseDTO<String> getOriginalUrl(@RequestBody GetOriginalUrlRequestVO requestVO) {
        final String shortUrl = requestVO.getShortUrl();
        log.info("getOriginalUrl, shortUrl: {}", shortUrl);
        try {
            if (StringUtils.isEmpty(shortUrl)) {
                return ResponseDTO.error("short url is empty");
            }
            final String originalUrl = shortUrlManager.getOriginalUrl(shortUrl);
            if (StringUtils.isEmpty(originalUrl)) {
                // 短域名不存在了或已过期
                return ResponseDTO.error("invalid short url");
            }
            return ResponseDTO.success(originalUrl);
        } catch (Exception e) {
            log.error("getOriginalUrl error", e);
            return ResponseDTO.error("getOriginalUrl error:" + e.getMessage());
        }
    }

}
