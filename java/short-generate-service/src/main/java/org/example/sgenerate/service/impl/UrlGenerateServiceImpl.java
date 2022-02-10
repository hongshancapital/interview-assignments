package org.example.sgenerate.service.impl;

import org.example.sgenerate.model.UrlMappingInfo;
import org.example.sgenerate.repository.UrlMappingStore;
import org.example.sgenerate.service.IUrlGenerateService;
import org.example.sgenerate.utils.Base62Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 链接信息生成接口实现类
 *
 * @author liuyadu
 */
@Service
public class UrlGenerateServiceImpl implements IUrlGenerateService {

    private UrlMappingStore store;

    public static final String DOMAIN = "https://s.cn/";

    public UrlGenerateServiceImpl(@Qualifier("inMemoryUrlMappingStore") UrlMappingStore store) {
        this.store = store;
    }


    /**
     * 创建短链接
     *
     * @param originalUrl 原始链接
     * @param expiryTime  过期时间
     * @return
     */
    @Override
    public UrlMappingInfo generateShortUrl(String originalUrl, Date expiryTime) {
        Assert.hasText(originalUrl, "url must not be empty");
        // 高并发场景下,可使用分布式发号系统代替.
        Long id = System.currentTimeMillis();
        String base62 = Base62Utils.encodeBase62(id);
        String shortUrl = DOMAIN + base62;
        UrlMappingInfo mappingInfo = new UrlMappingInfo();
        mappingInfo.setId(id.toString());
        mappingInfo.setExpiryTime(expiryTime);
        mappingInfo.setOriginalUrl(originalUrl);
        mappingInfo.setShortUrl(shortUrl);
        store.storeUrl(mappingInfo);
        return mappingInfo;
    }

    /**
     * 读取短链接信息
     *
     * @param shortUrl
     * @return
     */
    @Override
    public UrlMappingInfo readShortUrl(String shortUrl) {
        Assert.hasText(shortUrl, "url must not be empty");
        Assert.isTrue(shortUrl.startsWith(DOMAIN), "Invalid url:" + shortUrl);
        String encodeUrl = shortUrl.replaceFirst(DOMAIN, "");
        try {
            Long id = Base62Utils.decodeBase62(encodeUrl);
            return store.getUrl(id.toString());

        } catch (Exception e) {
            return null;
        }
    }
}
