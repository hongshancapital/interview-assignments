package com.example.demo.service;

import com.example.demo.bean.po.ShortUrlPo;
import com.example.demo.bean.request.OriginRequest;
import com.example.demo.bean.response.ShortLinkResponse;
import com.example.demo.bean.result.Result;
import com.example.demo.bean.result.Results;
import com.example.demo.config.AppConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@Service
public class ShortLinkService {

    @Resource
    private AppConfig appConfig;
    @Resource
    private HttpServletRequest request;
    @Resource
    private ShortLinkCache shortLinkCache;

    /**
     * 接受长域名信息，返回短域名信息
     *
     * @param originalUrl 长域名
     * @return 短域名
     */
    public ShortLinkResponse register(String originalUrl) {
        ShortLinkResponse res = new ShortLinkResponse();
        res.setOriginalUrl(originalUrl);
        res.setShortUrl(generateShortUrl(originalUrl));
        return res;
    }

    /**
     * 生成一个短连接，并缓存
     *
     * @param originalUrl 原始连接
     */
    private String generateShortUrl(String originalUrl) {
        ShortUrlPo shortUrlPo = shortLinkCache.next();
        String shortUrl = String.format("%s/%s", getDomain(), shortUrlPo.getCode());
        shortUrlPo.setOriginalUrl(originalUrl);
        shortUrlPo.setShortUrl(shortUrl);
        shortLinkCache.add(shortUrlPo.getCode(), shortUrlPo);
        return shortUrl;
    }

    private String getDomain() {
        if (appConfig.getShortDomain() != null) {
            return appConfig.getShortDomain().getPath();
        }
        String contextPath = request.getServletContext().getContextPath();
        StringBuffer url = request.getRequestURL();
        int index = url.length() - request.getRequestURI().length();
        return url.delete(index, url.length()).append(contextPath).toString();
    }

    public Result<String> getOriUrl(OriginRequest req) throws Exception {
        if (req.getShortUrl() == null || req.getShortUrl().length() == 0) {
            return Results.paramError("shortUrl 不能为空");
        }
        if (req.getShortUrl().startsWith("http")) {
            URI uri = new URI(req.getShortUrl());
            req.setShortUrl(uri.getPath().substring(1));
        }
        ShortUrlPo shortUrlPo = shortLinkCache.getShortUrl(req.getShortUrl());
        if (shortUrlPo == null) {
            return Results.paramError("短连接不存在");
        }
        return Results.ok(shortUrlPo.getOriginalUrl());
    }

    public ShortUrlPo getShortUrl(String code) {
        return shortLinkCache.getShortUrl(code);
    }

}
