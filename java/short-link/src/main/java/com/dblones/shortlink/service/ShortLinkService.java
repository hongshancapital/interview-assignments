package com.dblones.shortlink.service;

import com.dblones.shortlink.util.NumberUtils;
import com.dblones.shortlink.util.SerialNumberUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortLinkService {

    private static final Logger LOG = LoggerFactory.getLogger(ShortLinkService.class);

    private Map<String, String> longCache = new ConcurrentHashMap<>();

    private Map<String, String> shortCache = new ConcurrentHashMap<>();

    @Autowired
    private SerialNumberUtils serialNumberUtils;

    @Value("${short.domain}")
    private String shortDomainPrefix;

    @Value("${short.maxLenth}")
    private int shortMaxLength;

    public String transformUrl(String url) {
        LOG.info("长域名url:{}", url);
        final String md5 = DigestUtils.md5Hex(url);
        String shortUrl = longCache.get(md5);
        if (shortUrl == null){
            synchronized (md5.intern()){
                shortUrl = longCache.get(md5);
                if (shortUrl == null){
                    Long value = serialNumberUtils.getSerialNumber();
                    shortUrl = shortDomainPrefix + NumberUtils.compressNumber(value);
                    // 如果短域名长度超限，重新初始化序列数，覆盖老的短域名
                    if(shortUrl.length() > shortMaxLength){
                        value = serialNumberUtils.resetAndgetSerialNumber();
                        shortUrl = shortDomainPrefix + NumberUtils.compressNumber(value);
                        String oldLongUrl = shortCache.get(shortUrl);
                        // 清除老的长域名信息，防止内存溢出
                        if(oldLongUrl != null){
                            String oldMd5 = DigestUtils.md5Hex(oldLongUrl);
                            longCache.remove(oldMd5);
                        }
                    }
                    longCache.put(md5, shortUrl);
                    shortCache.put(shortUrl, url);
                }
            }

        }
        LOG.info("短域名url:{}", shortUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        LOG.info("短域名url:{}", shortUrl);
        String longUrl = shortCache.get(shortUrl);
        LOG.info("长域名url:{}", longUrl);
        if(longUrl == null){
            longUrl = "抱歉，原链接已过期销毁或原链接未存储";
        }
        return longUrl;
    }

}
