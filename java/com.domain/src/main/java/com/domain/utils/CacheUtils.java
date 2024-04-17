package com.domain.utils;

import com.domain.config.GlobalParametersConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class CacheUtils implements InitializingBean {

    @Autowired(required = false)
    private GlobalParametersConfig globalParametersConfig;  //全局配置

    private static  LoadingCache<String, RateLimiter>  REQUEST_CASH;  //缓存限流器

    private static Cache<String,Object> POST_CASH;  //写重复提交缓存
    /**
     * spring 初始化回调
     */
    @Override
    public void afterPropertiesSet()  {
        init();
    }
    /**
     * 初始化限流器
     */
    public void init()  {
        if(globalParametersConfig==null) return;
        Double limit= Double.valueOf(globalParametersConfig.getLimit());  //限流值
        REQUEST_CASH= CacheBuilder.newBuilder()
                 .maximumSize(1000000) //设置缓存上线
                 .expireAfterAccess(1,TimeUnit.DAYS)
                 .build(new CacheLoader<String, RateLimiter>() {
                    @Override
                    public RateLimiter load(String ipAddress) throws Exception {
                        //某人没有限流器 创建限流器
                        return RateLimiter.create(limit);
                    }
                });

        POST_CASH=CacheBuilder.newBuilder()
                .maximumSize(1000000) //设置缓存上线
                .expireAfterWrite(5,TimeUnit.SECONDS) //5秒没写 则移除
                .build();
    }
    /**
     * 限流检查
     * @param host  客户端地址
     * @return true不限/false限流
     */
    public Boolean checkLimit(String host){
        try {
            RateLimiter rateLimiter=REQUEST_CASH.get(host);
            if(rateLimiter!=null&&!rateLimiter.tryAcquire(1)) return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 提交重复检查
     * @param request  请求信息
     * @return true 存在 /false 不存在
     */
    public Boolean resubmit(String request){
        if(POST_CASH.getIfPresent(request)!=null) return true;
        return false;
    }
    /**
     * 提交重复标记
     * @param request  请求信息
     * @return true 存在 /false 不存在
     */
    public void submit(String request){
        POST_CASH.put(request,request);
    }
}
