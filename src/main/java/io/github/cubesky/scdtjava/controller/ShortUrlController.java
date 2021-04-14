package io.github.cubesky.scdtjava.controller;

import io.github.cubesky.scdtjava.entity.LongUrlBean;
import io.github.cubesky.scdtjava.entity.ShortUrlBean;
import io.github.cubesky.scdtjava.entity.UrlBean;
import io.github.cubesky.scdtjava.exception.RequestInvalidException;
import io.github.cubesky.scdtjava.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * URL 缩短服务 API 控制器
 */
@Api(tags = "API 服务")
@RestController
@RequestMapping("/api")
public class ShortUrlController {
    @Resource
    StorageService storageService;

    /**
     * 由短链接转换为长链接
     * @param bean 请求 JSON 结构体
     * @return UrlBean 结构体返回
     */
    @ApiOperation(value="转换为长链接")
    @PostMapping("/tolongurl")
    public ResponseEntity<UrlBean> shortUrlToLongUrl(@RequestBody ShortUrlBean bean) {
        //冗余检查
        if (bean == null || bean.getShortUrl() == null || bean.getShortUrl().isEmpty()) throw new RequestInvalidException();
        //查询对应长链接
        String longUrl = storageService.shortUrlToLongUrl(bean.getShortUrl());
        if (longUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UrlBean(bean.getShortUrl(), longUrl));
    }

    /**
     * 由长连接缩短
     * @param bean 请求 JSON 结构体
     * @return UrlBean 结构体返回
     */
    @ApiOperation(value="转换为短链接")
    @PostMapping("/toshorturl")
    public ResponseEntity<?> longUrlToShortUrl(@RequestBody LongUrlBean bean) {
        //冗余检查
        if (bean == null || bean.getLongUrl() == null || bean.getLongUrl().isEmpty()) throw new RequestInvalidException();
        //如果已有对应短链接则返回 如果没有则生成
        String shortUrl = storageService.longUrlToShortUrl(bean.getLongUrl());
        //如果满了 则不返回地址
        if (shortUrl == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(new UrlBean(shortUrl, bean.getLongUrl()));
    }
}
