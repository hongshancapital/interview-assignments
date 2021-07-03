package com.shorturl.service;

import com.shorturl.util.ShortUrlCreateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.shorturl.common.Constant.SHORT_URL_PREFIX;

@Service
public class ShortUrlService implements IUrlService {

    private static Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

    /**
     * 根据长链接返回短链接
     *
     * @param url 长链接
     * @return
     */
    @Override
    public String createShortUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        String shortText = ShortUrlCreateUtil.ShortText(url);
        String shortUrl = SHORT_URL_PREFIX + shortText;
        logger.info("shortUrl -> {}", shortUrl);
        return shortUrl;
    }

}
