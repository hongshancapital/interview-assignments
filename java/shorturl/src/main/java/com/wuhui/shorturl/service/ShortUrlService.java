package com.wuhui.shorturl.service;

import com.wuhui.shorturl.model.ShortUrl;
import com.wuhui.shorturl.repository.ShortUrlRepository;
import com.wuhui.shorturl.utils.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class ShortUrlService {

    @Resource
    private ShortUrlRepository shortUrlRepository;

    /**
     * 生成短链接并且入库
     *
     * @param sourceUrl 原始链接
     * @return 短链接
     */
    public String createShortUrl(String sourceUrl) {
        ShortUrl shortUrl = shortUrlRepository.findBySourceUrl(sourceUrl);
        if (shortUrl != null) {
            return NumberUtils.encode62(shortUrl.getId());
        }
        shortUrl = new ShortUrl()
                .setSourceUrl(sourceUrl)
                .setCreateTime(new Date());
        shortUrlRepository.save(shortUrl);
        return NumberUtils.encode62(shortUrl.getId());
    }

    /**
     * 短连接查找原始链接
     *
     * @param shortUrl 短链接
     * @return 原始链接
     */
    public String findSourceUrl(String shortUrl) {
        long id = NumberUtils.decode62(shortUrl);
        Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findById(id);
        return shortUrlOptional.orElseThrow(() -> new RuntimeException("无法找到匹配的链接"))
                .getSourceUrl();
    }

}
