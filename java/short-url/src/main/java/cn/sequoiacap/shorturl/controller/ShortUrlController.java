package cn.sequoiacap.shorturl.controller;

import cn.sequoiacap.shorturl.exception.StoreException;
import cn.sequoiacap.shorturl.service.ShortUrlService;
import com.google.common.collect.ImmutableSet;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@RestController
public class ShortUrlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);
    private static final Set<String> ALLOWED_PROTOCOLS = ImmutableSet.of("http", "https");

    private final ShortUrlService service;

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @ApiOperation(value = "根据长链接生成短链接")
    @GetMapping("/generate")
    public Response<String> generate(@ApiParam(value = "长链接", required = true) String originalUrl)
            throws StoreException {
        if (!StringUtils.hasLength(originalUrl)) {
            return Response.fail(ResponseStatus.PARAM_ERROR, "original url is null or empty");
        }

        URL url;
        try {
            url = new URL(originalUrl);
        } catch (MalformedURLException e) {
            LOGGER.warn("wrong original url: {}", originalUrl, e);
            return Response.fail(ResponseStatus.PARAM_ERROR, "original url is invalid");
        }

        if (!ALLOWED_PROTOCOLS.contains(url.getProtocol())) {
            return Response.fail(ResponseStatus.PARAM_ERROR, "protocol of original url is invalid");
        }

        String shortUrl = service.generate(originalUrl);
        if (shortUrl == null) {
            return Response.fail(ResponseStatus.INTERNAL_ERROR, "generate short url failed");
        }

        return Response.success(shortUrl);
    }

    @ApiOperation(value = "根据短链接获取长链接")
    @GetMapping("/{shortUrlId}")
    public Response<String> get(@PathVariable @ApiParam(value = "短链接 id", required = true) String shortUrlId)
            throws StoreException {
        if (shortUrlId == null || shortUrlId.length() > 8) {
            return Response.fail(ResponseStatus.PARAM_ERROR, "short url id is invalid");
        }

        String originalUrl = service.get(shortUrlId);
        if (originalUrl == null) {
            return Response.fail(ResponseStatus.NOT_FOUND);
        }

        return Response.success(originalUrl);
    }

    @ApiIgnore
    @GetMapping("/favicon.ico")
    void favicon() {
    }
}
