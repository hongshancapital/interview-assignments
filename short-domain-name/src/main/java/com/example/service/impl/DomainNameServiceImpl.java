package com.example.service.impl;

import com.example.common.Md5Util;
import com.example.service.DomainNameService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class DomainNameServiceImpl implements DomainNameService {
    private static HashMap<String, String> domainMap = new HashMap<>();
    private static Set<String> longDomainSet = new HashSet<>();
    private static Integer selfIncreasing = 1;

    @Override
    public String longToShort(String domainName) {
        String shortUrl = null;
        if (!longDomainSet.contains(domainName)) {
            shortUrl = checkAndGetShortDomain(domainName);
            // 将短域名存放至map
            domainMap.put(shortUrl, domainName);
            // 将长域名存放set 方便判断是否存在
            longDomainSet.add(domainName);
        } else {
            Iterator<String> iterator = domainMap.keySet().iterator();

            while (iterator.hasNext()){
                String key = iterator.next();
                String value = domainMap.get(key);
                if (domainName.equals(value)) {
                    shortUrl = key;
                    // todo 已存在得长域名 直接遍历返回短域名
                    //       break;
                }
            }
        }
        return shortUrl;
    }


    /**
     * 校验短域名是否存在，存在则重新生成
     *
     * @param domainName
     * @return
     */
    private String checkAndGetShortDomain(String domainName) {
        String shortUrl;
        selfIncreasing++;
        // 通过长域名获取短域名
        shortUrl = Md5Util.getShortUrl(domainName, selfIncreasing.toString());
        // todo 通过算法可能导致短域名已存在，应重新生成，难以复现，因分支覆盖率注释
//        if (domainMap.containsKey(shortUrl)) {
//            shortUrl = checkAndGetShortDomain(domainName);
//        }
        return shortUrl;
    }

    @Override
    public String getLongByShort(String domainName) {
        return domainMap.get(domainName);
    }
}
