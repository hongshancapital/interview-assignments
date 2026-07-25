package org.zxl.service;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxl.advise.CommonException;
import org.zxl.manager.DomainManager;

import java.util.regex.Pattern;

@Service
public class DomainService {
    //简单验证下
    private Pattern pattern = Pattern.compile("((http://)|(https://)).+");
    @Autowired
    private DomainManager domainManager;

    public boolean isValidUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return false;
        }
        return pattern.matcher(url).matches();
    }

    public String convertToShort(String url) {
        if (!isValidUrl(url)) {
            throw new CommonException("param illegal : url=" + url);
        }
        return domainManager.convertToShortUrl(url);
    }


    public String convertToLong(String url) {
        String longUrl = domainManager.convertToLongUrl(url);
        if (StringUtils.isBlank(longUrl)) {
            throw new CommonException("short url not exits : url=" + url);
        }
        return longUrl;
    }

}
