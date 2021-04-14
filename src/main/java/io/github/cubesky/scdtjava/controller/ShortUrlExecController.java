package io.github.cubesky.scdtjava.controller;

import io.github.cubesky.scdtjava.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短链接重定向控制器
 */
@Api(tags = "重定向")
@RestController
@RequestMapping("/s")
public class ShortUrlExecController {
    @Resource
    StorageService storageService;

    /**
     * 短链接重定向
     * @param path 短链接 hash
     * @return 返回重定向
     */
    @ApiOperation(value = "短链接重定向")
    @GetMapping("/{path}")
    public ResponseEntity<?> callUrl(@PathVariable String path) {
        //从 StorageService 查询缩短链接的对应长链接
        String longUrl = storageService.shortUrlToLongUrl(path);
        //如果返回的长链接为 null 说明没有此记录 返回重定向 404
        if (longUrl == null) return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/404.html").build();
        //返回重定向
        return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", longUrl).build();
    }
}
