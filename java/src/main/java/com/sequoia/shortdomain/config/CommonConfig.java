package com.sequoia.shortdomain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    //获取缓存最大的容量，超过此容量，应进行缓存项清理，防止内存溢出
    @Value("${short_domain_cache_max:20000}")
    private int shortDomainCacheMax;
    //获取缓存最小的容量，即给缓存一个初始值，减少缓存不够时，在临时扩容
    @Value("${short_domain_cache_min:10000}")
    private int shortDomainCacheMin;

    public int getShortDomainCacheClearNum() {
        return shortDomainCacheClearNum;
    }

    public void setShortDomainCacheClearNum(int shortDomainCacheClearNum) {
        this.shortDomainCacheClearNum = shortDomainCacheClearNum;
    }

    @Value("${short_domain_cache_clear_num:100}")
    private int shortDomainCacheClearNum;
    public int getShortDomainCacheMax() {
        return shortDomainCacheMax;
    }

    public void setShortDomainCacheMax(int shortDomainCacheMax) {
        this.shortDomainCacheMax = shortDomainCacheMax;
    }

    public int getShortDomainCacheMin() {
        return shortDomainCacheMin;
    }

    public void setShortDomainCacheMin(int shortDomainCacheMin) {
        this.shortDomainCacheMin = shortDomainCacheMin;
    }
}
