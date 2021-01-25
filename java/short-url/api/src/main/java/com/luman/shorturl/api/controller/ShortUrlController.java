package com.luman.shorturl.api.controller;

import com.luman.shorturl.api.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @apiDefine shortUrl
 * 短连接
 */

@RestController
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    /**
     * @api {POST} /gen 生成
     * @apiDescription 生成短连接
     * @apiGroup shortUrl
     * @apiHeader token 页面token
     *
     * @apiParam {String} url 地址
     * @apiParam {String} expire 有效期(天)
     *
     * @apiSuccess {String} url 短连接地址
     */
    @PostMapping("/gen")
    public ResponseEntity<String> gen(String url,Integer expire){
        if(StringUtils.isEmpty(url)||!Pattern.matches("^(http|https):/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?$",url)){
            return ResponseEntity.badRequest().body("url error");
        }
        if(expire != null && (expire < 0 || expire >365*10)){
            return ResponseEntity.badRequest().body("expire error");
        }

        return ResponseEntity.ok().body(shortUrlService.gen(url,expire));
    }



    @GetMapping("/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code){
        if(StringUtils.isEmpty(code)){
            return ResponseEntity.notFound().build();
        }
        String url = shortUrlService.getUrl(code);
        try {
            if(StringUtils.isEmpty(url)){
                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(new URI("/index")).build();
            }
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(new URI(url)).build();
        }catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }
}
