package com.scdt.interview.assignments.service.impl;

import com.scdt.interview.assignments.bean.dto.LinkInfo;
import com.scdt.interview.assignments.bean.vo.LongLinkParam;
import com.scdt.interview.assignments.bean.vo.ShortLinkParam;
import com.scdt.interview.assignments.service.LinkService;
import com.scdt.interview.assignments.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LinkServiceImpl implements LinkService {

    private static ConcurrentHashMap<String, String> urlMap = new ConcurrentHashMap(2<<15);//根据实际情况酌情初始化
    /**
     * (8G * 1024 * 1024 * 1024) * 0.9) * 0.75 / (128+8) = 42633866
     */
    private final static int MEMORY_TOTAL_LIMIT = 42633866;

    public LinkInfo generateShortUrl(LongLinkParam longLinkParam) {
        if (isBeyondLimit()) {
            throw new RuntimeException("out of memory size");
        }
        String shortUrl = UrlUtil.shortUrl(longLinkParam.getLongUrl());
        urlMap.put(shortUrl, longLinkParam.getLongUrl());
//        log.info("generateShortUrl.shortUrl==>{},LongUrl===>{}", shortUrl, longLinkParam.getLongUrl());
        return LinkInfo.builder().shortUrl(shortUrl).build();
    }

    public LinkInfo getLongUrl(ShortLinkParam shortLinkParam) {
        String longUrl = urlMap.get(shortLinkParam.getShortUrl());
        if(StringUtils.isBlank(longUrl)){
            log.warn("getLongUrl.shortUrl==>{}未找到longUrl", shortLinkParam.getShortUrl(), longUrl);
        }
        return LinkInfo.builder().longUrl(longUrl).build();
    }

    /**
     * 内存是超过限制
     *
     * @return
     */
    private Boolean isBeyondLimit() {
        // 假设map存储1条数据平均占用128+8,最多不能超过总内存90%,map扩容因子是0.75
        return urlMap.size() > MEMORY_TOTAL_LIMIT;
    }

}
