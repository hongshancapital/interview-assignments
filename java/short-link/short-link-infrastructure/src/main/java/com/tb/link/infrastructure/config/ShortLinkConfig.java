package com.tb.link.infrastructure.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author andy.lhc
 * @date 2022/4/17 8:21
 */
@Component
public class ShortLinkConfig {

    /**
     * 成成短码重试次数 = 5
     */
    @Value("${generate.retry.count.max}")
    private int shortLinkRetryCount;

    /**
     * 长链长度：4096
     */
    @Value("${origin.link.length}")
    private int originLinkLength;

    /**
     * 短码通用前缀
     */
    @Value("${short.link.common.prefix}")
    private String shortLinkPrefix;

    /**
     * 短链长度为 8位
     */
    @Value("${short.link.length}")
    private int shortLinkLength;

    /**
     * 长链域名白名单列表，后续可根据不同的appKey单独配置
     */
    @Value("${origin.link.domain.whiteList}")
    private String originLinkDomainWhiteList ;

    /**
     * appKey 白名单
     */
    @Value("${appKey.whiteList}")
    private String appKeyWhiteList ;

    public  int getShortLinkRetryCount() {
        return shortLinkRetryCount;
    }

    public int getOriginLinkLength() {
        return originLinkLength;
    }

    public String getShortLinkPrefix() {
        return shortLinkPrefix;
    }

    public int getShortLinkLength() {
        return shortLinkLength;
    }

    public List<String> getOriginLinkDomainWhiteList() {
        if(StringUtils.isBlank(originLinkDomainWhiteList) ){
            return new ArrayList<String>() ;
        }
        return Arrays.asList(originLinkDomainWhiteList.split(",")) ;
    }

    public List<String> getAppKeyWhiteList() {
        if(StringUtils.isBlank(appKeyWhiteList) ){
            return new ArrayList<String>() ;
        }
        return Arrays.asList(appKeyWhiteList.split(",")) ;
    }

}
