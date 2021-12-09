package com.bolord.shorturl.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bolord.shorturl.common.Result;
import com.bolord.shorturl.config.ShortUrlProperties;
import com.bolord.shorturl.exception.ShortUrlException;
import com.bolord.shorturl.service.ShortUrlService;
import com.bolord.shorturl.utils.UrlUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;

/**
 * 短链接资源接口
 *
 * @author alex
 */
@Api(description = "短链接服务")
@Order(Ordered.LOWEST_PRECEDENCE)
@RestController
@AllArgsConstructor
public class ShortUrlResource {

    private ShortUrlService shortUrlService;

    private ShortUrlProperties shortUrlProperties;

    private static URI defRedirectUrl;

    @PostConstruct
    public void init() {
        // 初始化默认重定向URI，避免每次构建 URI 对象
        defRedirectUrl = URI.create(shortUrlProperties.getDefaultRedirectUrl());
    }

    /**
     * 短链接转长链接
     *
     * @param id       短链接 ID
     * @param response response
     */
    @ApiOperation(value = "短链接转长链接", notes = "短链接转长链接，响应结果以 HTTP 302 重定向返回")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(
                    name = "id",
                    value = "短链接ID",
                    required = true,
                    dataType = "String",
                    paramType = "path")
        }
    )
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET
    )
    public void fromShortUrl(
            @PathVariable(name = "id") String id,
            @ApiIgnore ServerHttpResponse response
    ) {
        // ID 长度限制最大 8 个字符
        if (StringUtils.length(id) > 8) {
            fallbackDefaultRedirectUrl(response);
            return;
        }

        String url = shortUrlService.fromShortUrl(id);

        if (url == null) {
            fallbackDefaultRedirectUrl(response);
            return;
        }

        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create(url));
    }

    private void fallbackDefaultRedirectUrl(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.NOT_FOUND);
        response.getHeaders().setLocation(defRedirectUrl);
    }

    /**
     * 长链接转短链接
     *
     * @param url 长链接
     *
     * @return 短链接
     */
    @ApiOperation(value = "长链接转短链接", notes = "长链接转短链接，响应结果以 JSON 返回")
    @ApiImplicitParams({
        @ApiImplicitParam(
                name = "url",
                value = "长链接，只支持 http/https 协议的完整链接，需要 URL 编码",
                required = true,
                dataType = "String",
                paramType = "path"
        )
    })
    @RequestMapping(
            path = "/{url}",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public Mono<Result<String>> toShortUrl(@PathVariable(name = "url") String url)
            throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        validateUrl(decodedUrl);

        String shortUrl = shortUrlService.toShortUrl(decodedUrl);
        return Mono.just(new Result<>(true, shortUrl));
    }

    private void validateUrl(String url) {
        if (!UrlUtils.isValidUrl(url)) {
            throw new ShortUrlException(HttpStatus.BAD_REQUEST.value(), "不合法的链接");
        }
    }

}
