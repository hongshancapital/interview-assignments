package com.service.impl;

import com.service.DomainConversionService;
import com.utils.HashUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
* 长短域名转换实现类
* liangjiangwei
* Create on 2021/10/10
* */
@Service
public class DomainConversionServiceImpl implements DomainConversionService {

    private Logger logger = LoggerFactory.getLogger(DomainConversionServiceImpl.class);
    private static final String BASE_URL = "http://a.com/";

    /**
     * 存储短域名以长域名为key短域名为value
     */
    static Map<String, String> shortDomainUrlDataMap = new HashMap<>();
    /**
     * 存储长域名以短域名为key长域名为value
     */
    static Map<String, String> longDomainUrlDataMap = new HashMap<>();

    /**
     * 功能描述: 长域名获取长域名
     * @return java.lang.String
     * Create on 2021/10/10
     * @author liangjiangwei
     */
    @Override
    public String getShortDomainUrl(String longUrl) throws Exception {
            String shortUrl = shortDomainUrlDataMap.get(longUrl);
            if (StringUtils.isNotBlank(shortUrl)) {
                return shortUrl;
            }
            if(StringUtils.isNotBlank(longUrl)){
                shortUrl = BASE_URL + HashUtils.murmur32HashSeed(longUrl, (int) System.currentTimeMillis());
                shortDomainUrlDataMap.put(longUrl, shortUrl);
                longDomainUrlDataMap.put(shortUrl, longUrl);
                return shortUrl;
            }else{
                return "长域名不能为空";
            }
    }

    /**
     * 功能描述: 短域名获取长域名
     * @return java.lang.String
     * Create on 2021/10/10
     * @author liangjiangwei
     */
    @Override
    public String getLongDomainUrl(String shortUrl) throws Exception {
        return longDomainUrlDataMap.getOrDefault(shortUrl, "长域名不存在");
    }

}
