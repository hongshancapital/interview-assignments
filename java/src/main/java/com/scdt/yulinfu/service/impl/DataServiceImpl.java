package com.scdt.yulinfu.service.impl;

import com.scdt.yulinfu.service.DataService;
import com.scdt.yulinfu.service.DataStoreService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataStoreService dataStoreService;

    /**
     * 映射表
     */
    private static final String[] CODE_LIST = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "_", "-"
    };

    /**
     * 长度
     */
    private static final int LINK_LENGTH = 8;

    @Value("${base.url}")
    private String baseUrl;

    /**
     * 获取短链接
     *
     * @param longLink
     * @return
     */
    @Override
    public String getShortLink(String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return null;
        }
        return generateShortLink(longLink);
    }

    /**
     * 获取长链接
     *
     * @param shortLink
     * @return
     */
    @Override
    public String getLongLink(String shortLink) {
        return dataStoreService.getLongLink(shortLink);
    }

    private String generateShortLink(String longLink) {
        String shortLink = dataStoreService.getShortLink(longLink);
        if (!StringUtils.isEmpty(shortLink)) {
            return shortLink;
        }
        long current = dataStoreService.getCurrent();
        int base = CODE_LIST.length;
        StringBuilder shortLinkBuilder = new StringBuilder();
        for (int i = 0; i < LINK_LENGTH; i++) {
            int mod = (int)(current % base);
            long div = current / base;
            current = div;
            shortLinkBuilder.append(CODE_LIST[mod]);
        }
        shortLink = String.format("%s%s", baseUrl, shortLinkBuilder.toString());
        dataStoreService.saveData(shortLink, longLink);
        return shortLink;
    }
}
