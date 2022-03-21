package com.wuaping.shortlink.service;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.wuaping.shortlink.exception.ExceptionCodeMessage;
import com.wuaping.shortlink.exception.ServiceException;
import com.wuaping.shortlink.repository.ShortLinkRepository;
import com.wuaping.shortlink.util.Base62Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 短域名业务层
 *
 * @author Aping
 * @since 2022/3/16 21:50
 */
@Service
@Slf4j
public class ShortLinkService {

    /**
     * 自增index
     */
    private final AtomicLong index = new AtomicLong(1000000L);

    /**
     * 短域名最大长度
     */
    private static final int MAX_LENGTH = 8;

    /**
     * 字符串常量池
     */
    private final Interner<String> internPool = Interners.newWeakInterner();

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @Value("${short-link.hostname}")
    private String hostname;

    public String toShortLink(String originalLink) {

        // 幂等判断
        String shortLink = shortLinkRepository.findByOriginalLink(originalLink);
        if (StringUtils.isNotBlank(shortLink)) {
            // 二次确认短域名是否存在，因为两份缓存分开独立，无法保证同步驱逐
            if (StringUtils.isNotBlank(shortLinkRepository.findByShortLink(shortLink))) {
                return shortLink;
            }
        }

        synchronized (internPool.intern(originalLink)) {
            // 二次幂等判断
            shortLink = shortLinkRepository.findByOriginalLink(originalLink);
            if (StringUtils.isNotBlank(shortLink)) {
                // 二次确认短域名是否存在
                if (StringUtils.isNotBlank(shortLinkRepository.findByShortLink(shortLink))) {
                    return shortLink;
                }
            }

            shortLink = generateShortLink();

            log.info("生成短链: {}", shortLink);
            if (shortLink.length() > MAX_LENGTH) {
                throw new ServiceException("短域名用尽");
            }

            shortLink = hostname + "/" + shortLink;

            shortLinkRepository.save(shortLink, originalLink);

        }

        return shortLink;
    }


    public String originalLink(String shortLink) {

        String originalLink = shortLinkRepository.findByShortLink(shortLink);
        if (StringUtils.isBlank(originalLink)) {
            throw new ServiceException(ExceptionCodeMessage.LINK_EXPIRED);
        }

        return originalLink;
    }


    private String generateShortLink() {
        return Base62Util.encodeToLong(index.incrementAndGet());
    }

}
