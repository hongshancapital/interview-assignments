package com.scdt.controller;

import com.scdt.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 短域名Controller层
 *
 * @author penghai
 * @date 2022/5/3
 */
@Api("短域名服务相关接口")
@RestController
@RequestMapping("/url-map")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @ApiOperation("获取长域名对应的短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名")
    @PostMapping("/get-short-url")
    public ResponseEntity<String> getShortUrl(@RequestBody String longUrl) {
        if (!isValidLongUrl(longUrl)) {
            return ResponseEntity.internalServerError().build();
        }

        Optional<String> optionalShortUrl = shortUrlService.getShortUrl(longUrl);

        return optionalShortUrl.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation("获取短域名对应的长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名")
    @PostMapping("/get-long-url")
    public ResponseEntity<String> getLongUrl(@RequestBody String shortUrl) {
        if (!isValidShortUrl(shortUrl)) {
            return ResponseEntity.internalServerError().build();
        }

        Optional<String> optionalLongUrl = shortUrlService.getLongUrl(shortUrl);

        return optionalLongUrl.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 正则表达式方式的判断长域名的合法性
    private static boolean isValidLongUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()){
            return false ;
        }

        String resultUrl = longUrl.toLowerCase();
        String regEx = "^((https|http|ftp|rtsp|mms)?://)"  //https、http、ftp、rtsp、mms
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

        return resultUrl.matches(regEx);
    }

    // 正则表达式方式的判断短域名合法性
    private static boolean isValidShortUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()){
            return false ;
        }
        String regEx = "^([0-9a-zA-Z]{1,8})$";
        return shortUrl.matches(regEx);
    }
}
