package cn.leirq.demoshorturl.controller;

import cn.leirq.demoshorturl.service.ShortUrlBizService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 短url转换api层
 * 
 * @author Ricky
 */
@RestController
public class UrlController {

    @Autowired
    private ShortUrlBizService shortUrlBizService;

    @Value("${default.short.domain:ab.cn}")
    private String defaultShortDomain;

    @ApiOperation(value = "获取短url")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longUrl", value = "长url", dataType = "String"),
            @ApiImplicitParam(name = "shortDomain", value = "指定的短域名", dataType = "String")
    })
    @GetMapping("getShortUrl")
    public Mono<String> getShortUrl(String longUrl, String shortDomain) {
        if (StringUtils.isBlank(shortDomain)) {
            // 没有指定短域名就用默认域名
            shortDomain = defaultShortDomain;
        }
        return Mono.just(shortDomain + "/" + shortUrlBizService.createAndSaveByLongUrl(longUrl));
    }

    @ApiOperation(value = "获取长url")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortUrl", value = "短url", dataType = "String")
    })
    @GetMapping("getLongUrl")
    public Mono<String> getLongUrl(String shortUrl) {
        return Mono.just(shortUrlBizService.getLongUrlByShortUrl(shortUrl));
    }
}
