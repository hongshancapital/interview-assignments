package com.github.shwas1.shorturl.service;

import com.github.shwas1.shorturl.dao.ShortUrlDAO;
import com.github.shwas1.shorturl.model.ShortUrlPO;
import com.github.shwas1.shorturl.service.generator.ShortPathGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 短链接业务处理层
 */
@Service
public class ShortUrlService {

    private static final String HTTP_SCHEMA = "https";
    @Autowired
    private ShortPathGenerator shortPathGenerator;
    @Autowired
    private ShortUrlDAO shortUrlDAO;

    @Value("${short.url.domain}")
    private String domain;

    /**
     * 生成短链接
     *
     * @param originalUrl 原始链接
     * @return 短链接
     */
    public String generateShortUrl(String originalUrl) {
        ShortUrlPO shortUrlPO = new ShortUrlPO();
        String shortUrl = String.format("%s://%s/%s", HTTP_SCHEMA, domain, shortPathGenerator.generate());
        shortUrlPO.setShortUrl(shortUrl);
        shortUrlPO.setOriginalUrl(originalUrl);

        shortUrlPO = shortUrlDAO.save(shortUrlPO);
        return shortUrlPO.getShortUrl();
    }

    /**
     * 还原原始链接
     *
     * @param shortUrl 短链接
     * @return 原始链接
     */
    public String revertOriginalUrl(String shortUrl) {
        ShortUrlPO shortUrlPO = shortUrlDAO.getByShortUrl(shortUrl);
        Assert.notNull(shortUrlPO, "短链接不存在：" + shortUrl);

        return shortUrlPO.getOriginalUrl();
    }
}
