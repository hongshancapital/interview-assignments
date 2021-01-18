package com.wb.shorturl.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.shorturl.entity.ShortUrl;
import org.springframework.stereotype.Service;

/**
 * @author bing.wang
 * @date 2021/1/8
 */
@Service
public interface ShortUrlService extends IService<ShortUrl> {

    /**
     * 根据短码查询长网址
     *
     * @param code the shortCode
     * @return the originUrl
     */
    public String getOriginUrlByShortCode(String code);

    /**
     * 根据长网址查询短码
     *
     * @param url the originUrl
     * @return the shortCode
     */
    public String getShortCodeByOriginUrl(String url);

}
