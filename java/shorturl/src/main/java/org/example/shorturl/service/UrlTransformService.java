package org.example.shorturl.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.common.MyException;
import org.example.shorturl.generator.IdGeneratorWrapper;
import org.example.shorturl.properties.UrlTransformProperty;
import org.example.shorturl.util.ShortUrlUtil;
import org.example.shorturl.util.UrlCacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * url转化服务
 *
 * @author bai
 * @date 2022/3/19 20:43
 */
@Slf4j
@Service
public class UrlTransformService {
    /** 前缀 http */
    private static final String HTTP = "http://";
    /** 前缀 https */
    private static final String HTTPS = "https://";
    /** 摘要算法工具 */
    private static final Digester DIGESTER = new Digester(DigestAlgorithm.MD5);
    /** id生成器包装器 */
    @Resource
    private IdGeneratorWrapper idGeneratorWrapper;
    @Resource
    private UrlTransformProperty property;
    
    /**
     * 通过长链接获取短链接
     *
     * @param longUrl 长链接
     * @return {@link String}
     */
    public String shortUrl(String longUrl) {
        //去掉前缀
        if (StrUtil.startWith(longUrl, HTTPS, true)) {
            longUrl = StrUtil.subAfter(longUrl, HTTPS, false);
        }
        if (StrUtil.startWith(longUrl, HTTP, true)) {
            longUrl = StrUtil.subAfter(longUrl, HTTP, false);
        }
        //获取长连接的信息摘要
        String digestHex = DIGESTER.digestHex(longUrl);
        //是否命中缓存
        if (UrlCacheUtil.shortUrlInstance().containsKey(digestHex)) {
            String shortCode = UrlCacheUtil.shortUrlInstance().get(digestHex);
            //返回短链接
            return ShortUrlUtil.getShortUrl(shortCode);
        }
        else {
            //生成新的code
            String shortCode = ShortUrlUtil.decimalToSixtyTwo(idGeneratorWrapper.nextLongId());
            //缓存digestHex-->短链接code
            UrlCacheUtil.shortUrlInstance().put(digestHex, shortCode);
            //缓存短链接code-->longUrl
            UrlCacheUtil.longUrlInstance().put(shortCode, longUrl);
            //返回短链接
            return ShortUrlUtil.getShortUrl(shortCode);
        }
    }
    
    /**
     * 通过短链接获取长链接
     *
     * @param shortUrl 短链接
     * @return {@link String}
     */
    public String longUrl(String shortUrl) {
        //去掉前缀
        if (StrUtil.startWith(shortUrl, HTTPS, true)) {
            shortUrl = StrUtil.subAfter(shortUrl, HTTPS, false);
        }
        if (StrUtil.startWith(shortUrl, HTTP, true)) {
            shortUrl = StrUtil.subAfter(shortUrl, HTTP, false);
        }
        //是否为本服务生成的
        if (StrUtil.startWith(shortUrl, property.getPrefix())) {
            String shortCode = StrUtil.subAfter(shortUrl, property.getPrefix() + "/", false);
            if (!UrlCacheUtil.longUrlInstance().containsKey(shortCode)) {
                log.error("链接已过期 {}", shortUrl);
                throw new MyException("链接已过期");
            }
            return UrlCacheUtil.longUrlInstance().get(shortCode);
        }
        else {
            log.error("链接非法 {}", shortUrl);
            throw new MyException("链接非法");
        }
    }
}

