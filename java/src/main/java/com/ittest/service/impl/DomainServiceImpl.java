package com.ittest.service.impl;

import com.ittest.service.DomainService;
import com.ittest.utils.UrlGenerateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: taojiangbing
 * @Date: 2022/4/20 18:14
 * @Description:
 */
@Service
public class DomainServiceImpl implements DomainService {

    private Map<String, String> map = new ConcurrentHashMap<>(16);

    private static final Integer LENGTH = 8;


    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longDomain 长域名
     * @return 返回值
     */
    @Override
    public String storage(String longDomain) {
        // 1.随机生成字符串 根据a-z,A-Z,0-9随机生成
        String shortDomain = UrlGenerateUtil.generate("abc",8,longDomain);
        // 2.存储
        // 要求存储在jvm中即可，实际上存在redis中会更好点，如果流量太大了，必须采用服务器集群，放在jvm中,会存在session不一致问题
        while (!StringUtils.isEmpty(map.get(shortDomain))) {
            //如果碰到重复的短域名，则重新生成一个,不要覆盖之前的，正常情况下这里不会出现太多次重复
            shortDomain = UrlGenerateUtil.generate("abc",8,longDomain);
        }
        map.put(shortDomain, longDomain);
        System.out.println(map.size());
        return shortDomain;
    }


    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     *
     * @param shortDomain 短连接
     * @return 返回值
     */
    @Override
    public String transfor(String shortDomain) {
        return map.get(shortDomain);
    }

}
