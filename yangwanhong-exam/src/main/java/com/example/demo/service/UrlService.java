package com.example.demo.service;

import com.example.demo.util.EncodeUtil;
import com.example.demo.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UrlService {

    /**
     * 生成短域名
     * @param url
     * @return
     */
    public String getShortUrl(String url) {
        /**
         * 生成唯一ID对应长域名，并保存对应关系
         */
        Long id = IdUtil.getGlobalId();
        log.info("id:{}-url:{}",id,url);

        String shortUrl = EncodeUtil.getShortUrlByLongNum(id);
        EncodeUtil.urlMap.put(id,url);

        return shortUrl;
    }

    /**
     * 获取短域名对应的长域名
     * @param url
     * @return
     */
    public String convertToLongUrl(String url) {
        Long number = EncodeUtil.getLongNumByShortUrl(url);
        return EncodeUtil.urlMap.get(number);
    }
}
