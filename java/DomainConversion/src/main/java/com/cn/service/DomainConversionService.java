package com.cn.service;

import com.cn.cache.DataPool;
import com.cn.exception.DomainConversionException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wukui
 * @date 2021--12--29
 **/
@Service
public class DomainConversionService {
    private static final Logger log = LoggerFactory.getLogger(DomainConversionService.class);
    @Value("${domain.short.length}")
    private int shortDomainLength ;
    @Value("${domain.short.check}")
    private boolean check;
    @Autowired
    public DataPool dataPool;
    @Test
    public void TestBuilderShortDomain(){
        Assert.assertNotNull(builderShortDomain("http://tt.cc","ddd"));
    }

    public String builderShortDomain(String rootDomain, String longDomain) {
//        log.info("longDomain:"+longDomain);
        rootDomain = verificationDomain(rootDomain);
        verificationURL(longDomain);
        String shortDomain = getAvailableShortDomain(rootDomain,longDomain);
        dataPool.setData(shortDomain, longDomain);
        return shortDomain;
    }

    public void clearDomainCache(){
        dataPool.clearData();
    }

//    public String checkShortDomain(String shortDomain, String longDomain) {
//        if (check) {
//            if (!longDomain.equals(dataPool.getData(shortDomain))) {
//                shortDomain = getAvailableShortDomain(longDomain);
//                return checkShortDomain(shortDomain, longDomain);
//            }
//        }
//        return shortDomain;
//    }

    public String findLongDomain(String shortDomain) {
        return dataPool.getData(shortDomain);
    }

    /**
     * @param longDomain 原域名
     * @return 可用短域名
     */
    public String getAvailableShortDomain(String rootDomain,String longDomain) {
        String[] shortDomains = getShortDomains(longDomain);
        String tempValue;
        //碰撞检查 找出可用的短字符
        for (String tempShortDomain : shortDomains) {
            String shortDomain = rootDomain + tempShortDomain;
            tempValue = dataPool.getData(shortDomain);
            if (longDomain.equals(tempValue))
                return shortDomain;
            if(!StringUtils.hasText(tempValue))
                return shortDomain;
        }
        //如果可用的短字符都用完了，则改变URL再次计算
        return getAvailableShortDomain(rootDomain,longDomain + "str");
    }

    public String[] getShortDomains(String longDomain) {
        return DomainUtil.shortUrl(longDomain, shortDomainLength);
    }


    //校验url合法性
    public void verificationURL(String url) {
        if (!StringUtils.hasText(url))
            throw new DomainConversionException("500", "需要转换的域名为空");
    }

    public String verificationDomain(String domain) {
        if (!StringUtils.hasText(domain))
            throw new DomainConversionException("500", "目标短域名为空");
        if (!domain.startsWith("http://") && !domain.startsWith("https://"))
            throw new DomainConversionException("500", "非法短域名");
        if (!domain.endsWith("/"))
            return domain + "/";
        return domain;
    }

}
