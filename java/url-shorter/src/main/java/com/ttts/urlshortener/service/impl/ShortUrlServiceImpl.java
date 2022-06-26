package com.ttts.urlshortener.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.ttts.urlshortener.base.util.NumberRadixUtils;
import com.ttts.urlshortener.business.ShortUrlBusiness;
import com.ttts.urlshortener.domain.ShortUrl;
import com.ttts.urlshortener.service.ShortUrlService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    //有效时间，单位天
    @Value(value = "${shorturl.expiryday}")
    private int expiryDay;

    private ShortUrlBusiness shortUrlBusiness;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlBusiness shortUrlBusiness) {
        this.shortUrlBusiness = shortUrlBusiness;
    }

    @Override
    public ShortUrl add(Long surl, String lurl) {
        LocalDateTime now = LocalDateTime.now();

        ShortUrl value = new ShortUrl();
        value.setId(IdUtil.getSnowflakeNextId());
        value.setSurl(surl);
        value.setLurl(lurl);
        value.setLmd5(SecureUtil.md5(lurl));
        value.setCrateTime(now);
        value.setExpiresTime(now.plusDays(expiryDay));

        shortUrlBusiness.add(value);
        return value;
    }

    @Override
    public int deleteById(Long id) {
        return shortUrlBusiness.deleteById(id);
    }

    @Override
    public ShortUrl getBySurl(Long surl) {
        return shortUrlBusiness.getBySurl(surl);
    }

    @Override
    public List<ShortUrl> getByLurl(String lurl) {
        return shortUrlBusiness.getByLurl(lurl);
    }

    @Override
    public List<ShortUrl> listAllShortUrl() {
        return shortUrlBusiness.listAllShortUrl();
    }

    /**
     * 查询过期数据，并清楚
     */
    @Override
    public void handleExpired() {
        //TODO
    }
}
