package cn.office.luxixi.urlservice.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.office.luxixi.urlservice.pojo.Result;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.concurrent.ExecutionException;

/**
 * url 换取 controller
 * xuchao
 */
@Api(tags = "短连接模块")
@Validated
@RestController
@RequestMapping("/url")
@Slf4j
public class UrlController {

    @Value("${domain.name}")
    private String DOMAIN_NAME;

    LoadingCache<String, String> shortToLongCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "";
                }
            });
    LoadingCache<Integer, String> longToShortCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(new CacheLoader<Integer, String>() {
                @Override
                public String load(Integer integer) throws Exception {
                    return "";
                }
            });

    @ApiOperation(value = "长链接换短连接")
    @GetMapping("/short")
    public Result<String> getShort(@RequestParam @NotNull @Size(min = 1) String url) {
        log.debug("domainName:{}", DOMAIN_NAME);
        //
        String r = null;
        Integer hash = url.hashCode();
        String shortUrl = null;
        try {
            if ((r = longToShortCache.get(hash)) != null && !StrUtil.isBlankIfStr(r)) {
                if (!StrUtil.isBlankIfStr(shortToLongCache.get(r))) {
                    // 双向检查下
                    return Result.ok("", DOMAIN_NAME + r);
                } else {
                    longToShortCache.put(hash, null);
                }
            } else {
                int i = 3;
                do {
                    i--;
                    r = RandomUtil.randomString(8);
                } while (!StrUtil.isBlankIfStr(shortToLongCache.get(r)) && i > 0);
            }
            if (!StrUtil.isBlankIfStr(shortToLongCache.get(r))) {
                log.debug("r:{}", r);
                return Result.error("碰撞", null);
            }
            longToShortCache.put(hash, r);
            shortToLongCache.put(r, url);
            return Result.ok("", DOMAIN_NAME + r);
        } catch (ExecutionException e) {
            log.error("exception class:{}, exception message:{}", e.getClass().getSimpleName(), e.getMessage());
            return Result.error(e.getMessage(), null);
        }
    }

    @ApiOperation(value = "短连接换长链接")
    @GetMapping("/long")
    public Result<String> getLong(@RequestParam @NotNull @Size(min = 1) String url) {
        if (url.contains(DOMAIN_NAME)) {
            url = url.replace(DOMAIN_NAME, "");
        } else {
            return Result.error("非本服务的单链接", null);
        }
        try {
            if (!StrUtil.isBlankIfStr(shortToLongCache.get(url))) {
                return Result.ok("", shortToLongCache.get(url));
            } else {
                return Result.error("缓存失效", null);
            }
        } catch (ExecutionException e) {
            log.error("exception class:{}, exception message:{}", e.getClass().getSimpleName(), e.getMessage());
            return Result.error(e.getMessage(), null);
        }
    }
}
