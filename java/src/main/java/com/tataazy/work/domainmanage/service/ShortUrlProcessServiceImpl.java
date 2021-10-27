package com.tataazy.work.domainmanage.service;

import com.tataazy.work.domainmanage.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlProcessServiceImpl {
    public static final Log log = LogFactory.getLog(ShortUrlProcessServiceImpl.class);

    private volatile Map<String, String> urlMap, recordMap;

    @PostConstruct
    public void init() {
        urlMap = new ConcurrentHashMap<>();
        recordMap = new ConcurrentHashMap<>();
    }

    private static final char[] CHARACTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z','0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public String generateShorUrl(String normalUrl) {
        if (StringUtils.isBlank(normalUrl)) {
            return null;
        }
        try {
            String sMD5Result = Md5Utils.encodeByMd5(normalUrl);
            String shortUrl = recordMap.get(sMD5Result);
            if (StringUtils.isNotBlank(shortUrl)) {
                return shortUrl;
            }

            StringBuilder resultUrl = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                String subString = sMD5Result.substring(i*8, i*8 + 8);
                long hexLong = 0x3FFFFFFF & Long.parseLong(subString,16);
                String outChars = "";
                for (int j = 0; j < 2; j++) {
                    long index = 0x0000003D & hexLong;
                    outChars += CHARACTERS[(int)index];
                    hexLong = hexLong >> 15;
                }
                resultUrl.append(outChars);
            }
            shortUrl = resultUrl.toString();
            urlMap.put(shortUrl, normalUrl);
            recordMap.put(sMD5Result, shortUrl);
            String preFix = normalUrl.substring(0, normalUrl.indexOf(":"));
            return preFix + "://" + shortUrl;
        } catch (Exception e) {
            log.error("generateShorUrl exception : ", e);
        }
        return null;
    }

    public String getNormalUrl(String shortUrl) {
        if (StringUtils.isNotBlank(shortUrl)) {
            return urlMap.get(shortUrl.substring(shortUrl.lastIndexOf("/") + 1));
        }
        return null;
    }

}
